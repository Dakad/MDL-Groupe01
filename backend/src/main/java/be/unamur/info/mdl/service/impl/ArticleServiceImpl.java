package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.entity.ArticleEntity;
import be.unamur.info.mdl.dal.entity.AuthorEntity;
import be.unamur.info.mdl.dal.entity.TagEntity;
import be.unamur.info.mdl.dal.entity.UserEntity;
import be.unamur.info.mdl.dal.repository.ArticleRepository;
import be.unamur.info.mdl.dal.repository.AuthorRepository;
import be.unamur.info.mdl.dal.repository.TagRepository;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.ArticleService;
import be.unamur.info.mdl.service.exceptions.ArticleAlreadyExistException;
import com.github.slugify.Slugify;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("articleService")
@Transactional
public class ArticleServiceImpl implements ArticleService {

  private ArticleRepository articleRepository;
  private final UserRepository userRepository;
  private final TagRepository tagRepository;
  private final AuthorRepository authorRepository;

  private final Slugify slugify = new Slugify();


  @Autowired
  public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository,
    TagRepository tagRepository,
    AuthorRepository authorRepository) {
    this.articleRepository = articleRepository;
    this.userRepository = userRepository;
    this.tagRepository = tagRepository;
    this.authorRepository = authorRepository;
  }

  @Override
  public boolean create(ArticleDTO articleData, UserDTO currentUser)
    throws ArticleAlreadyExistException {

    if (articleRepository.existsByReference(articleData.getReference())) {
      throw new ArticleAlreadyExistException(
        "The reference : " + articleData.getTitle() + " is already saved.");
    }

    if (articleRepository.existsByTitle(articleData.getTitle())) {
      throw new ArticleAlreadyExistException(articleData.getTitle() + " is already taken.");
    }

    // Create a newDTO with the basic data sent (title, abstract, price, year, ...)
    ArticleEntity newArticle = ArticleEntity.of(articleData);

    UserEntity creator = userRepository.findByUsername(currentUser.getUsername());
    newArticle.setCreator(creator);

    // Set the category or create a new one
    this.attachCategory(newArticle, articleData.getCategory());

    this.attachAuthors(newArticle, articleData.getAuthors());

    this.attachKeywords(newArticle, articleData.getKeywordList());

    newArticle.setCreatedAt(LocalDate.now());
    this.articleRepository.save(newArticle);
    return true;
  }


  /**
   * Attach a new category or the one persisted in DB.
   * @param newArticle - The new Article being created
   * @param categoryName - The category name
   */
  private void attachCategory(ArticleEntity newArticle, String categoryName) {
    TagEntity category = this.getOrCreateTag(categoryName);
    category.getArticlesByCategory().add(newArticle);
    newArticle.setCategory(category);
  }

  /**
   * Get the matching tag from the repository or create a new one.
   * @param tagName  The tag name
   * @return the persisted or created Tag
   */
  private TagEntity getOrCreateTag(String tagName) {
    String slug = this.slugify.slugify(tagName);
    Optional<TagEntity> dbTag = this.tagRepository.findBySlug(slug);

    TagEntity tag;
    if (dbTag.isPresent()) {
      tag = dbTag.get();
    } else {
      tag = TagEntity.builder().name(tagName.trim()).slug(slug).build();
    }
    return tag;
  }

  /**
   * Attach the corresponding Author(created or persisted) to the new article
   * @param newArticle  The new Article being created
   * @param authors     The author's name list.
   */
  private void attachAuthors(ArticleEntity newArticle, List<String> authors) {
    Set<AuthorEntity> list = new LinkedHashSet<>(authors.size());

    for (String authorName : authors) {
      String authorSlug = this.slugify.slugify(authorName);
      AuthorEntity newAuthor = AuthorEntity.builder().name(authorName).slug(authorSlug).build();
      AuthorEntity author = this.authorRepository.findByName(authorName).orElse(newAuthor);
      list.add(author);
    }
    newArticle.setAuthors(list);
  }

  /**
   * Attach the corresponding Author(created or persisted) to the new article
   * @param newArticle - The new Article being created
   * @param keywords - The author's name list.
   */
  private void attachKeywords(ArticleEntity newArticle, Set<String> keywords) {
    Set<TagEntity> list = new LinkedHashSet<>(keywords.size());
    TagEntity keyword;

    for (String keywordName : keywords) {
      keyword = getOrCreateTag(keywordName);
      keyword.getArticlesByKeyword().add(newArticle);
      list.add(keyword);
    }

    newArticle.setKeywords(list);
  }

}

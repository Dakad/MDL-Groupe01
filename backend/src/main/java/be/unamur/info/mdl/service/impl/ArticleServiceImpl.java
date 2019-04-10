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
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

  private ArticleRepository articleRepository;
  private final UserRepository userRepository;
  private final TagRepository tagRepository;
  private final AuthorRepository authorRepository;


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
    UserEntity creator = userRepository.findByUsername(currentUser.getUsername());

    if (articleRepository.existsByReference(articleData.getReference())) {
      throw new ArticleAlreadyExistException(
        "The reference : " + articleData.getTitle() + " is already saved.");
    }

    if (articleRepository.existsByTitle(articleData.getTitle())) {
      throw new ArticleAlreadyExistException(articleData.getTitle() + " is already taken.");
    }

    // Create a newDTO with the basic data sent (title, abstract, price, year, ...)
    ArticleEntity newArticle = ArticleEntity.of(articleData);
    newArticle.setCreator(creator);

    TagEntity category = new TagEntity();
    category.setName(articleData.getCategory());
    newArticle.setCategory(category);

    // For each author sent, check if already persisted
    // Otherwise, just create new Author with the name sent
    for (String authorName : articleData.getAuthors()) {
      AuthorEntity newAuthor = AuthorEntity.builder().name(authorName).build();
      AuthorEntity author = this.authorRepository.findByName(authorName).orElse(newAuthor);
      newArticle.getAuthors().add(author);
    }

    // Same process as the authors for the keywords
    for (String keywordName : articleData.getKeywordList()) {
      TagEntity newKeyword = new TagEntity();
      newKeyword.setName(keywordName);
      TagEntity keyword = this.tagRepository.findById(keywordName).orElse(newKeyword);
      newArticle.getKeywords().add(keyword);
    }

    this.articleRepository.save(newArticle);
    return true;
  }


}

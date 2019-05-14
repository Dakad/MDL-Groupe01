package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.entity.ArticleEntity;
import be.unamur.info.mdl.dal.entity.AuthorEntity;
import be.unamur.info.mdl.dal.entity.BookmarkEntity;
import be.unamur.info.mdl.dal.entity.TagEntity;
import be.unamur.info.mdl.dal.entity.UserEntity;
import be.unamur.info.mdl.dal.repository.ArticleRepository;
import be.unamur.info.mdl.dal.repository.AuthorRepository;
import be.unamur.info.mdl.dal.repository.BookmarkRepository;
import be.unamur.info.mdl.dal.repository.TagRepository;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.exceptions.AlreadyBookmarkedException;
import be.unamur.info.mdl.exceptions.ArticleAlreadyExistException;
import be.unamur.info.mdl.exceptions.ArticleNotFoundException;
import be.unamur.info.mdl.exceptions.BookmarkNotFoundException;
import be.unamur.info.mdl.service.ArticleService;
import com.github.slugify.Slugify;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service("articleService")
@Transactional
public class ArticleServiceImpl implements ArticleService {

  private ArticleRepository articleRepository;
  private final UserRepository userRepository;
  private final TagRepository tagRepository;
  private final AuthorRepository authorRepository;
  private final BookmarkRepository bookmarkRepository;

  private final Slugify slugify = new Slugify();


  @Autowired
  public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository,
    TagRepository tagRepository,
    AuthorRepository authorRepository, BookmarkRepository bookmarkRepository) {
    this.articleRepository = articleRepository;
    this.userRepository = userRepository;
    this.tagRepository = tagRepository;
    this.authorRepository = authorRepository;
    this.bookmarkRepository = bookmarkRepository;
  }


  @Override
  public ArticleDTO getArticleByReference(String reference) throws ArticleNotFoundException {
    if (reference == null) {
      throw new IllegalArgumentException("The reference must be defined");
    }
    Optional<ArticleEntity> dbArticle = this.articleRepository.findByReference(reference);
    if (!dbArticle.isPresent()) {
      throw new ArticleNotFoundException("The referenced article was not found");
    } else {
      ArticleEntity article = dbArticle.get();
      article.setNbViews(article.getNbViews() + 1);
      this.articleRepository.save(article);
      return article.toDTO();
    }
  }


  @Override
  public List<ArticleDTO> listArticleByReferences(List<String> references) {
    Sort sortByViews = Sort.by("nbViews", "createdAt").descending();
    return this.articleRepository.findDistinctFirst5ByReferenceIsIn(references, sortByViews)
      .map(a -> a.toDTO()).collect(
        Collectors.toList());
  }

  @Override
  public Map<String, List<ArticleDTO>> listArticleByCategories(List<String> categoryNames) {
    Map<String, List<ArticleDTO>> articlesByCategory = new HashMap<>();
    Sort sortByViews = Sort.by("nbViews", "createdAt").descending();

    // First, find all categories provided
    List<TagEntity> categories = this.tagRepository.findByNameOrSlugIn(categoryNames);

    categories.forEach(category -> {
      // Find the article by category and transform to DTO
      List<ArticleDTO> articles = this.articleRepository
        .findDistinctFirst5ByCategory(category, sortByViews)
        .map(a -> a.toDTO())
        .collect(Collectors.toList());

      articlesByCategory.put(category.getName(), articles);
    });

    return articlesByCategory;
  }

  @Override
  public boolean create(ArticleDTO articleData, UserDTO currentUser)
    throws ArticleAlreadyExistException {

    if (articleRepository.existsByReference(articleData.getReference())) {
      throw new ArticleAlreadyExistException(
        "This article is already saved : " + articleData.getReference());
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

    this.articleRepository.save(newArticle);
    return true;
  }


  /**
   * Attach a new category or the one persisted in DB.
   *
   * @param newArticle - The new Article being created
   * @param categoryName - The category name
   */
  private void attachCategory(ArticleEntity newArticle, String categoryName) {
    TagEntity category = ServiceUtils.getOrCreateTag(categoryName, this.tagRepository);
    category.getArticlesByCategory().add(newArticle);
    newArticle.setCategory(category);
  }


  /**
   * Attach the corresponding Author(created or persisted) to the new article
   *
   * @param newArticle The new Article being created
   * @param authors The author's name list.
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
   *
   * @param newArticle - The new Article being created
   * @param keywords - The keyword's name list.
   */
  private void attachKeywords(ArticleEntity newArticle, Set<String> keywords) {
    Set<TagEntity> list = new LinkedHashSet<>(keywords.size());
    TagEntity keyword;

    for (String keywordName : keywords) {
      keyword = ServiceUtils.getOrCreateTag(keywordName, this.tagRepository);
      keyword.getArticlesByKeyword().add(newArticle);
      list.add(keyword);
    }

    newArticle.setKeywords(list);
  }

  @Override
  public boolean addBookmark(String reference, String username, String note)
    throws ArticleNotFoundException, AlreadyBookmarkedException {

    Optional<ArticleEntity> article;
    article = articleRepository.findByReference(reference);
    if (!article.isPresent()) {
      throw new ArticleNotFoundException("The requested article was not found");
    }
    //Check if the user has already bookmarked this article
    UserEntity user = userRepository.findByUsername(username);
    if (user.getBookmarks().stream().anyMatch(b -> b.getArticle().equals(article.get()))) {
      throw new AlreadyBookmarkedException("This article is already in your bookmarks");
    }

    BookmarkEntity bookmark = new BookmarkEntity();
    bookmark.setArticle(article.get());
    bookmark.setCreator(user);
    bookmark.setNote(note);
    user.getBookmarks().add(bookmark);
    article.get().getBookmarks().add(bookmark);

    return true;
  }


  @Override
  public boolean isBookmarked(String reference, String username) throws ArticleNotFoundException {
    Optional<ArticleEntity> article = articleRepository.findByReference(reference);
    if (!article.isPresent()) {
      throw new ArticleNotFoundException("The requested article was not found");
    }
    UserEntity user = userRepository.findByUsername(username);
    return bookmarkRepository.existsByCreatorAndArticle(user, article.get());
  }


  @Override
  public boolean removeBookmark(String reference, String username)
    throws ArticleNotFoundException, BookmarkNotFoundException {
    Optional<ArticleEntity> article = articleRepository.findByReference(reference);
    if (!article.isPresent()) {
      throw new ArticleNotFoundException("");
    }
    UserEntity user = userRepository.findByUsername(username);
    Optional<BookmarkEntity> bookmark = bookmarkRepository
      .findByCreatorAndArticle(user, article.get());
    if (!bookmark.isPresent()) {
      throw new BookmarkNotFoundException("The request article was not present in the bookmarks");
    }

    user.getBookmarks().remove(bookmark.get());
    article.get().getBookmarks().remove(bookmark.get());

    userRepository.save(user);
    articleRepository.save(article.get());
    bookmarkRepository.delete(bookmark.get());
    return true;
  }

  @Override
  public List<ArticleDTO> getSubscriptions(String username, int page) {
    Pageable pageable = PageRequest.of(page - 1, 20, Sort.by("created_at").descending());
    UserEntity user = userRepository.findByUsername(username);
    return articleRepository.findDistinctByFollower(username, pageable).map(a -> a.toDTO())
      .collect(Collectors.toList());
  }

  @Override
  public List<ArticleDTO> getRecommended(String username, int page) {
    //Pageable sorting on score, descending
    Pageable pageable = PageRequest.of(page - 1, 20, Sort.by("score").descending());
    //case of user not connected : get all articles
    if (username == null) {
      return articleRepository.findAll(pageable).stream().map(a -> a.toDTO())
        .collect(Collectors.toList());
    }

    UserEntity user = userRepository.findByUsername(username);

    //The list of a user's bookmarked articles' references
    List<String> references = user.getBookmarks().stream().map(a -> a.getArticle().getReference())
      .collect(Collectors.toList());

    //case where a user has no bookmarks -- this one exists because when the list is empty no article is returned
    if (references.isEmpty()) {
      return articleRepository.findAll(pageable).stream().map(a -> a.toDTO())
        .collect(Collectors.toList());
    }
    //case of user domain not defined, only looking at bookmarks
    if (user.getDomain() == null) {
      return articleRepository.findByReferenceNotIn(references, pageable).map(a -> a.toDTO())
        .collect(Collectors.toList());
    }
    //case of user domain defined, looking at domain and bookmarks
    return articleRepository.findByCategoryLikeOrCategoryInAndReferenceNotIn(user.getDomain(),
      user.getTags(), references, pageable)
      .map(a -> a.toDTO()).collect(Collectors.toList());
  }

  @Override
  public void updateScores() {
    List<ArticleEntity> articles = articleRepository.findAll();
    articles.forEach(article -> {
      int nbDays = 1;
      int nbBookmarked = article.getNbBookmarks();

      if (!article.getCreatedAt().equals(LocalDate.now())) {
        // Older implies more relevant
        nbDays = (int) article.getCreatedAt().until(LocalDate.now(), ChronoUnit.DAYS);
      }

      // More views, more juicy
      float score = article.getNbViews() / nbDays;

      score = score + 3 * (nbBookmarked / nbDays); // More bookmark, more relevant
      score = score + 0.3f * (article.getNbCitations() / nbDays); // More citation, more relevant

      article.setScore(score);
    });

    articleRepository.saveAll(articles);
  }

}

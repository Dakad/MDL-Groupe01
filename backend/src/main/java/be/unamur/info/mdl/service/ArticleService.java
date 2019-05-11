package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.exceptions.AlreadyBookmarkedException;
import be.unamur.info.mdl.exceptions.ArticleAlreadyExistException;
import be.unamur.info.mdl.exceptions.ArticleNotFoundException;
import be.unamur.info.mdl.exceptions.BookmarkNotFoundException;
import java.util.List;
import java.util.Map;

public interface ArticleService {

  boolean create(ArticleDTO article, UserDTO creator) throws ArticleAlreadyExistException;

  /**
   * Retrieve a specific article
   *
   * @param reference The reference of wanted article
   * @return The referenced article
   */
  ArticleDTO getArticleByReference(String reference) throws ArticleNotFoundException;

  /**
   * Adds an article to the connected user's bookmark list.
   *
   * @param reference the reference to the article
   * @param username the username of the connected user
   * @return true if the article has successfully been added to the user's bookmark list, else false
   * @throws ArticleNotFoundException if the reference corresponds to no article
   */
  boolean addBookmark(String reference, String username, String note)
    throws ArticleNotFoundException, AlreadyBookmarkedException;

  /**
   * Removes a bookmarked article from a user's list of bookmarked articles
   *
   * @param reference the reference to the article
   * @param username the user's username
   * @return true if the removal succeeded and false else
   * @throws ArticleNotFoundException if the reference corresponds to no article
   * @throws BookmarkNotFoundException if the bookmark is not found
   */
  boolean removeBookmark(String reference, String username)
    throws ArticleNotFoundException, BookmarkNotFoundException;

  boolean isBookmarked(String reference, String username) throws ArticleNotFoundException;

  /**
   * Retrieve a list of article based their references
   *
   * @param references The provided references
   * @return The list of referenced articles
   */
  List<ArticleDTO> listArticleByReferences(List<String> references);

  /**
   * Retrieve a list of article based their categories
   *
   * @param categories The provided categories
   * @return The list of referenced articles
   */
  Map<String, List<ArticleDTO>> listArticleByCategories(List<String> categories);

  List<ArticleDTO> getSubscriptions(String username, int page);

  List<ArticleDTO> getRecommended(String username, int page);

  /**
   * Update the score of all articles
   */
  void updateScores();
}

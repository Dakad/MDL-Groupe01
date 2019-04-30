package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.exceptions.ArticleAlreadyExistException;
import be.unamur.info.mdl.service.exceptions.ArticleNotFoundException;

public interface ArticleService {

  boolean create(ArticleDTO article, UserDTO creator) throws ArticleAlreadyExistException;

  /**
   * Retrieve a specific article
   * @param reference The reference of wanted article
   * @return The referenced article
   */
  ArticleDTO getArticleByReference(String reference) throws ArticleNotFoundException;

  /**
   * Adds an article to the connected user's bookmark list.
   * @param reference the reference to the article
   * @param username the username of the connected user
   * @return true if the article has successfully been added to the user's bookmark list, else false
   * @throws ArticleNotFoundException if the reference corresponds to no article
   */
  boolean addBookmark(String reference, String username, String note) throws ArticleNotFoundException;
}

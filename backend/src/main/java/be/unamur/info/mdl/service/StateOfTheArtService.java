package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.StateOfTheArtDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.exceptions.ArticleNotFoundException;
import be.unamur.info.mdl.service.exceptions.BookmarkNotFoundException;
import be.unamur.info.mdl.service.exceptions.SotaAlreadyExistException;
import be.unamur.info.mdl.service.exceptions.SotatNotFoundException;

public interface StateOfTheArtService {


  /**
   * Retrieve a SoTA specified by the provided reference
   *
   * @param reference The provided reference
   * @return The data of the matching SoTA.
   * @throws IllegalArgumentException if the provided reference is null.
   * @throws SotatNotFoundException The referenced SoTA is not found in persistence.
   */
  StateOfTheArtDTO getSotaByReference(String reference) throws SotatNotFoundException;


  /**
   * Persist a new SoTA.
   *
   * @param data The data of the new SoTA.
   * @param creator The creator of this SoTA.
   * @return The sucessfuly persisted SoTA
   * @throws SotaAlreadyExistException The provided SoTA data is already saved.
   * @throws ArticleNotFoundException If the provided article reference is not in persistence.
   */
  StateOfTheArtDTO create(StateOfTheArtDTO data, UserDTO creator)
    throws SotaAlreadyExistException, ArticleNotFoundException;

  /**
   * Adds a state of the art to the connected user's bookmark list.
   *
   * @param reference the reference to the state of the art
   * @param username the username of the connected user
   * @return true if the state of the art has successfully been added to the user's bookmark list, else false
   * @throws SotatNotFoundException if the reference corresponds to no state of the art
   */
  boolean addBookmark(String reference, String username, String note)
    throws SotatNotFoundException;

  /**
   * Removes a bookmarked state of the art from a user's list of bookmarked articles
   *
   * @param reference the reference to the state of the art
   * @param username the user's username
   * @return true if the removal succeeded and false else
   * @throws SotatNotFoundException if the reference corresponds to no article
   * @throws BookmarkNotFoundException if the bookmark is not found
   */
  boolean removeBookmark(String reference, String username)
    throws SotatNotFoundException, BookmarkNotFoundException;

  boolean isBookmarked(String reference, String username) throws SotatNotFoundException;
}

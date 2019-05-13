package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.CredentialDTO;
import be.unamur.info.mdl.dto.PasswordChangeDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.exceptions.InvalidCredentialException;
import be.unamur.info.mdl.exceptions.RegistrationException;
import be.unamur.info.mdl.exceptions.UserNotFoundException;
import javax.validation.Valid;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  /**
   * @return true if the user has been added otherwise false
   */
  boolean signin(UserDTO user) throws RegistrationException;

  /**
   * @return true if the login is successful and false if not
   */
  String login(@Valid CredentialDTO user) throws InvalidCredentialException;


  boolean changePassword(String username, PasswordChangeDTO passwordChangeDTO);


  /**
   * Check if the current user is followed by an User
   *
   * @param byUser - The user following
   * @param currentUser - The current followed
   * @return true if the current user is followed by the User
   * @throws UserNotFoundException if the username provided doesn't match any User.
   */
  boolean isFollowed(String byUser, String currentUser) throws UserNotFoundException;


  /**
   * @param username the username of the user to be followed
   * @param follower the username of the connected user
   * @return false if the user is already followed, else true
   */
  boolean follow(String username, String follower) throws UserNotFoundException;

  /**
   * @param username the username of the user to be unfollowed
   * @param follower the username of the connected user
   * @return false if the user was not followed, else true
   */
  boolean unfollow(String username, String follower) throws UserNotFoundException;

}

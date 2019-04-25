package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.CredentialDTO;
import be.unamur.info.mdl.dto.PasswordChangeDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.exceptions.InvalidCredentialException;
import be.unamur.info.mdl.service.exceptions.RegistrationException;
import javax.validation.Valid;

import be.unamur.info.mdl.service.exceptions.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;

public interface UserService extends UserDetailsService {

  /**
   * @return true if the user has been added otherwise false
   */
  public boolean signin(UserDTO user) throws RegistrationException;

  /**
   * @return true if the login is successful and false if not
   */
  public String login(@Valid CredentialDTO user) throws InvalidCredentialException;

  public boolean changePassword(String username, PasswordChangeDTO passwordChangeDTO);

  /**
   * @param username the username of the user to be followed
   * @param follower the username of the connected user
   * @return false if the user is already followed, else true
   * @throws UsernameNotFoundException
   */
  boolean follow(String username, String follower) throws UsernameNotFoundException;

  /**
   * @param username the username of the user to be unfollowed
   * @param follower the username of the connected user
   * @return false if the user was not followed, else true
   * @throws UsernameNotFoundException
   */
  boolean unfollow(String username, String follower) throws UsernameNotFoundException;

}

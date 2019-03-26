package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.CredentialDTO;
import be.unamur.info.mdl.dto.PasswordChangeDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.exceptions.InvalidCredentialException;
import be.unamur.info.mdl.service.exceptions.RegistrationException;
import javax.validation.Valid;

public interface UserService {

  /**
   * @return true if the user has been added otherwise false
   */
  public boolean signin(UserDTO user) throws RegistrationException;

  /**
   * @return true if the login is successful and false if not
   */
  public String login(@Valid CredentialDTO user) throws InvalidCredentialException;

  public boolean changePassword(String username, PasswordChangeDTO passwordChangeDTO);

}

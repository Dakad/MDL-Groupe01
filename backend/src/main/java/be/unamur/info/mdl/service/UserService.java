package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.CredentialDTO;
import be.unamur.info.mdl.dto.PasswordChangeDTO;
import be.unamur.info.mdl.dto.UserDTO;

public interface UserService {

  /**
   * @return true if the user has been added
   *         otherwise false
   */
    public boolean register(UserDTO user);

    /**
     * @return true if the login is successful and false if not
     */
    public boolean login(CredentialDTO user);

    public boolean changePassword(String username, PasswordChangeDTO passwordChangeDTO);

}
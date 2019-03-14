package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dal.entity.User;
import be.unamur.info.mdl.dto.CredentialDTO;
import be.unamur.info.mdl.dto.UserDTO;

public interface UserService {


    public boolean addUser(User user);

    /**
     * @return true if the login is successful and false if not
     */
    public boolean login(CredentialDTO user);

}
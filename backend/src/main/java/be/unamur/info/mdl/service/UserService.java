package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dal.entity.User;
import be.unamur.info.mdl.dto.UserDTO;

public interface UserService {


    public boolean addUser(User user);

    /**
     * @return true if the login is successful and false if not
     */
    public boolean login(UserDTO user);

    /**
     * A method used within the login method
     * @param loginCredentials an object containing the login credentials
     * @param databaseUser an object containing a user entity's data
     * @return true if the UserDTO passwords match
     */
    public Boolean checkPassword(UserDTO loginCredentials, UserDTO databaseUser);

}
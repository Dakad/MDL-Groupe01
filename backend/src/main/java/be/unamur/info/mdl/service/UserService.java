package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dal.entity.User;

public interface UserService {


    public boolean addUser(User user);

    /**
     *
     * @return true if the login is successful and false if not
     */
    public boolean login(String username, String password);

    /**
     * A method used within the login method
     * @param user the User object matching the username
     * @param password
     * @return true if the given password matches the user object's
     */
    public Boolean checkPassword(User user, String password);

}
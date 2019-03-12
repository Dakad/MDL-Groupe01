package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dal.entity.User;

public interface UserService {

  /**
   * Persit a new User in DB.
   * @param user - The new user to persist
   * @param rawPassword - The input password of the new user
   * @return true if the user is persisted otherwise false
   */
  boolean addUser(User user, String rawPassword);


}
package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.*;
import be.unamur.info.mdl.exceptions.InvalidProfilePictureLinkException;
import be.unamur.info.mdl.exceptions.UserNotFoundException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface ProfileService {

  /**
   * @param username the user's username
   * @return a DTO containing all the basic user data
   */
  ProfileBasicInfoDTO getBasicInfo(String username) throws UserNotFoundException;

  ProfileProInfoDTO getProInfo(String username) throws UserNotFoundException;


  /**
   * @param username the user's username
   * @return a DTO containing all the social information
   */
  ProfileSocialInfoDTO getSocialInfo(String username) throws UserNotFoundException;


  List<UserDTO> getFollowers(String username, int page) throws UserNotFoundException;


  List<UserDTO> getFollows(String username, int page) throws UserNotFoundException;

  List<BookmarkDTO> getBookmarks(String username, int page) throws UserNotFoundException;

  boolean update(ProfileUpdateDTO updateDTO, String username) throws InvalidProfilePictureLinkException;

}

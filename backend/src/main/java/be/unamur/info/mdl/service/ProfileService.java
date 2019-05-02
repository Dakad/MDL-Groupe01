package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.*;
import be.unamur.info.mdl.service.exceptions.UsernameNotFoundException;
import java.util.List;
import java.util.Map;

public interface ProfileService {

  /**
   * @param username the user's username
   * @return a DTO containing all the basic user data
   */
  ProfileBasicInfoDTO getBasicInfo(String username) throws UsernameNotFoundException;

  ProfileProInfoDTO getProInfo(String username) throws UsernameNotFoundException;


  /**
   * @param username the user's username
   * @return a DTO containing all the social information
   */
  ProfileSocialInfoDTO getSocialInfo(String username) throws UsernameNotFoundException;

  List<UserDTO> getFollowers(String username, int page) throws UsernameNotFoundException;

  List<UserDTO> getFollows(String username, int page) throws UsernameNotFoundException;

  List<BookmarkDTO> getBookmarks(String username, int page) throws UsernameNotFoundException;
}

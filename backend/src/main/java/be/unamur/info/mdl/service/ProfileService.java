package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.ProfileBasicInfoDTO;
import be.unamur.info.mdl.dto.ProfileProInfoDTO;
import be.unamur.info.mdl.dto.ProfileSocialInfoDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.exceptions.UsernameNotFoundException;
import java.util.Map;
import org.springframework.data.util.Pair;
import java.util.List;

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

  Map<String, String> getBookmarks(String username, int page) throws UsernameNotFoundException;
}

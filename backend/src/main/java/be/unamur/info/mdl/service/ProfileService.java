package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.ProfileBasicInfoDTO;
import be.unamur.info.mdl.dto.ProfileSocialInfoDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.exceptions.UsernameNotFoundException;
import io.micrometer.core.lang.Nullable;

import java.util.List;

public interface ProfileService{
  /**
   * @param username the user's username
   * @return a DTO containing all the basic user data
   * @throws UsernameNotFoundException
   */
  ProfileBasicInfoDTO getBasicInfo(String username) throws UsernameNotFoundException;

  /**
   *
   * @param username the user's username
   * @return a DTO containing all the social information
   * @throws UsernameNotFoundException
   */
  ProfileSocialInfoDTO getSocialInfo(String username) throws UsernameNotFoundException;

  List<UserDTO> getFollowers(String username, int page) throws UsernameNotFoundException;

  List<UserDTO> getFollows(String username, int page) throws UsernameNotFoundException;

  boolean isFollowed(String username, String user) throws UsernameNotFoundException;
}

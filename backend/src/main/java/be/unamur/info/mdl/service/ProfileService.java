package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.ProfileBasicInfoDTO;
import be.unamur.info.mdl.dto.ProfileProInfoDTO;
import be.unamur.info.mdl.service.exceptions.UsernameNotFoundException;
import io.micrometer.core.lang.Nullable;

public interface ProfileService{
  /**
   * @param username the user's username
   * @return a DTO containing all the basic user data
   */
  public ProfileBasicInfoDTO getBasicInfo(String username) throws UsernameNotFoundException;

  ProfileProInfoDTO getProInfo(String username) throws UsernameNotFoundException;
}

package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.ProfileBasicInfoDTO;

public interface ProfileService {
  /**
   * @param id the user's id
   * @return a DTO containing all the basic user data
   */
  public ProfileBasicInfoDTO getBasicInfo(String id);
}

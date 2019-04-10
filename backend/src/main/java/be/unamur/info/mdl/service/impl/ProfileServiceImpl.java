package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.ProfileBasicInfoDTO;
import be.unamur.info.mdl.service.ProfileService;
import org.springframework.stereotype.Service;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService {

  private UserRepository userRepository;

  @Override
  public ProfileBasicInfoDTO getBasicInfo(String id) {
    return userRepository.findById(id).toProfileBasicInfoDTO();
  }
}

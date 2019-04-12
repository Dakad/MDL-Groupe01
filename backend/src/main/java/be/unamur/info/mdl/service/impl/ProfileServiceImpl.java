package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.ProfileBasicInfoDTO;
import be.unamur.info.mdl.service.ProfileService;
import org.springframework.stereotype.Service;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService {

  private UserRepository userRepository;

  @Override
  public ProfileBasicInfoDTO getBasicInfo(String username) {
    if (!userRepository.existsByUsername(username)) return null;
    return userRepository.findByUsername(username).toProfileBasicInfoDTO();
  }
}

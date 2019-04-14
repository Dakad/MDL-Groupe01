package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.ProfileBasicInfoDTO;
import be.unamur.info.mdl.dto.ProfileSocialInfoDTO;
import be.unamur.info.mdl.service.ProfileService;
import be.unamur.info.mdl.service.exceptions.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService {

  private UserRepository userRepository;

  @Autowired
  public ProfileServiceImpl(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  @Override
  public ProfileBasicInfoDTO getBasicInfo(String username) throws UsernameNotFoundException {
    if (!userRepository.existsByUsername(username)) throw new UsernameNotFoundException();
    return userRepository.findByUsername(username).toProfileBasicInfoDTO();
  }

  @Override
  public ProfileSocialInfoDTO getSocialInfo(String username) throws UsernameNotFoundException{
    if (!userRepository.existsByUsername(username)) throw new UsernameNotFoundException();
    return userRepository.findByUsername(username).toProfileSocialInfoDTO();
  }
}

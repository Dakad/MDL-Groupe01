package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.entity.User;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.CredentialDTO;
import be.unamur.info.mdl.dto.PasswordChangeDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.UserService;
import be.unamur.info.mdl.service.exceptions.RegistrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is responsible of all possible services related to the /api/user
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;


  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  @Override
  public boolean register(UserDTO newUser) throws RegistrationException {
    // TODO Check if the userDTO is not null
    if (newUser == null) {
      throw new RegistrationException("Empty user data received.");
    }

    if (userRepository.findByUsername(newUser.getUsername()) != null) {
      throw new RegistrationException(newUser.getUsername() + " is already taken.");
    }

    if (userRepository.findByEmail(newUser.getEmail()) != null) {
      throw new RegistrationException(newUser.getEmail() + " is already taken.");
    }

    // userRepository.save(newUser);
    return true;
  }


  @Override
  public boolean login(CredentialDTO userLogin) {
    UserDTO userEntity = userRepository.findByUsername(userLogin.getUsername()).toDTO();
    if (checkPassword(userLogin, userEntity)) {
      return true;
    }
    return false;
  }


  @Override
  public boolean changePassword(String username, PasswordChangeDTO passwordChangeDTO) {
    User user = userRepository.findByUsername(username);
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    if (encoder.matches(passwordChangeDTO.getOldPassword(), user.getPassword())) {
      user.setPassword(passwordChangeDTO.getNewPassword());
      return true;
    }
    return false;
  }


  private boolean checkPassword(CredentialDTO userLogin, UserDTO userEntity) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.matches(userLogin.getPassword(), userEntity.getPassword());
  }
}
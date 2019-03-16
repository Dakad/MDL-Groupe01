package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.entity.User;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.CredentialDTO;
import be.unamur.info.mdl.dto.PasswordChangeDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.UserService;
import be.unamur.info.mdl.service.exceptions.RegistrationException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is responsible of all possible services related to the /api/user
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;


  @Autowired
  public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }


  @Override
  public boolean register(UserDTO userData) throws RegistrationException {
    if (userData == null) {
      throw new RegistrationException("Empty user data received.");
    }

    if (userRepository.findByUsername(userData.getUsername()) != null) {
      throw new RegistrationException(userData.getUsername() + " is already taken.");
    }

    if (userRepository.findByEmail(userData.getEmail()) != null) {
      throw new RegistrationException(userData.getEmail() + " is already taken.");
    }

    userData.setPassword(this.passwordEncoder.encode(userData.getPassword()));

    this.userRepository.save(User.of(userData));

    return true;
  }


  @Override
  public boolean login(@Valid CredentialDTO userLogin) {
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
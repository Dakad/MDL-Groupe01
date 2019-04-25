package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.config.security.SecurityUtils;
import be.unamur.info.mdl.dal.entity.UserEntity;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.CredentialDTO;
import be.unamur.info.mdl.dto.PasswordChangeDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.UserService;
import be.unamur.info.mdl.service.exceptions.InvalidCredentialException;
import be.unamur.info.mdl.service.exceptions.RegistrationException;
import java.util.Collections;
import javax.validation.Valid;

import be.unamur.info.mdl.service.exceptions.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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

  private PasswordEncoder passwordEncoder;


  @Autowired
  public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }


  @Override
  public boolean signin(UserDTO userData) throws RegistrationException {
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

    this.userRepository.save(UserEntity.of(userData));

    return true;
  }


  @Override
  public UserDetails loadUserByUsername(String username) throws org.springframework.security.core.userdetails.UsernameNotFoundException {
    if (this.userRepository.existsByUsername(username)){
      throw new org.springframework.security.core.userdetails.UsernameNotFoundException(username);
    }
    CredentialDTO credential = this.userRepository.findByUsername(username).toDTO();
    return new User(credential.getUsername(), credential.getPassword(), Collections.EMPTY_LIST);
  }


  @Override
  public String login(@Valid CredentialDTO credential) throws InvalidCredentialException {
    if (this.userRepository.existsByUsername(credential.getUsername())){
      CredentialDTO userCredential = userRepository.findByUsername(credential.getUsername()).toDTO();
      if (checkPassword(credential, userCredential)) {
        return SecurityUtils.generateToken(userCredential.getUsername());
      }
    }
    throw new InvalidCredentialException("Invalid username or password provided");
  }

  private boolean checkPassword(CredentialDTO userLogin, CredentialDTO userEntity) {
    return this.passwordEncoder.matches(userLogin.getPassword(), userEntity.getPassword());
  }


  @Override
  public boolean changePassword(String username, PasswordChangeDTO passwordChangeDTO) {
    UserEntity user = userRepository.findByUsername(username);
    if (this.passwordEncoder.matches(passwordChangeDTO.getOldPassword(), user.getPassword())) {
      user.setPassword(passwordChangeDTO.getNewPassword());
      return true;
    }
    return false;
  }

  @Override
  public boolean follow(String username, String follower) throws UsernameNotFoundException {
    if(!userRepository.existsByUsername(username)) throw new UsernameNotFoundException();
    UserEntity userfollower = userRepository.findByUsername(follower);
    UserEntity userfollowed = userRepository.findByUsername(username);
    if(!userfollower.getFollows().contains(userfollowed)) {
      userfollower.getFollows().add(userfollowed);
      userRepository.save(userfollower);
      return true;
    }
    return false;
  }

}

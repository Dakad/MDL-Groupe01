package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.config.security.SecurityUtils;
import be.unamur.info.mdl.dal.entity.UserEntity;
import be.unamur.info.mdl.dal.entity.UserProfileEntity;
import be.unamur.info.mdl.dal.repository.UserProfileRepository;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.CredentialDTO;
import be.unamur.info.mdl.dto.PasswordChangeDTO;
import be.unamur.info.mdl.dto.ProfileBasicInfoDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.UserService;
import be.unamur.info.mdl.exceptions.InvalidCredentialException;
import be.unamur.info.mdl.exceptions.RegistrationException;
import be.unamur.info.mdl.exceptions.UserNotFoundException;
import java.util.Collections;
import javax.validation.Valid;
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
  private UserProfileRepository userProfileRepository;
  private PasswordEncoder passwordEncoder;


  @Autowired
  public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,
    UserProfileRepository userProfileRepository) {
    this.userRepository = userRepository;
    this.userProfileRepository = userProfileRepository;
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

    // Create a new Profile
    UserProfileEntity profile = UserProfileEntity.builder()
      .profilePictureURL(ProfileBasicInfoDTO.DEFAULT_PROFILE_PICTURE_URL).build();

    profile = this.userProfileRepository.save(profile);

    UserEntity newUser = UserEntity.of(userData);
    newUser.setUserProfile(profile);

    this.userRepository.save(newUser);
    return true;
  }


  @Override
  public UserDetails loadUserByUsername(String username)
    throws org.springframework.security.core.userdetails.UsernameNotFoundException {
    if (this.userRepository.existsByUsername(username)) {
      throw new org.springframework.security.core.userdetails.UsernameNotFoundException(username);
    }
    CredentialDTO credential = this.userRepository.findByUsername(username).toDTO();
    return new User(credential.getUsername(), credential.getPassword(), Collections.emptyList());
  }


  @Override
  public String login(@Valid CredentialDTO credential) throws InvalidCredentialException {
    if (this.userRepository.existsByUsername(credential.getUsername())) {
      UserEntity dbUser = userRepository.findByUsername(credential.getUsername());
      if (this.passwordEncoder.matches(credential.getPassword(), dbUser.getPassword())) {
        return SecurityUtils.generateToken(dbUser.getUsername());
      }
    }
    throw new InvalidCredentialException("Invalid username or password provided");
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
  public boolean follow(String username, String follower) throws UserNotFoundException {
    if (!this.isFollowed(username, follower)) {
      UserEntity userFollower = userRepository.findByUsername(follower);
      UserEntity userFollowed = userRepository.findByUsername(username);
      userFollower.getFollows().add(userFollowed);
      if (!userFollowed.getFollowers().contains(userFollower)) {
        userFollowed.getFollowers().add(userFollower);
      }
      userRepository.save(userFollower);
      userRepository.save(userFollower);
      return true;
    }
    return false;
  }


  @Override
  public boolean isFollowed(String username, String user) throws UserNotFoundException {
    if (!userRepository.existsByUsername(username)) {
      throw new UserNotFoundException();
    }
    UserEntity currentUser = userRepository.findByUsername(user);
    UserEntity visitedUser = userRepository.findByUsername(username);
    return currentUser.getFollows().contains(visitedUser);
  }

  @Override
  public boolean unfollow(String username, String follower) throws UserNotFoundException {
    if (this.isFollowed(username, follower)) {
      UserEntity userFollower = userRepository.findByUsername(follower);
      UserEntity userFollowed = userRepository.findByUsername(username);

      userFollowed.getFollowers().remove(userFollower);
      userFollower.getFollows().remove(userFollowed);

      userRepository.save(userFollower);
      return true;
    }
    throw new UserNotFoundException();
  }
}

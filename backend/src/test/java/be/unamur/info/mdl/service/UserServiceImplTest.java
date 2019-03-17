package be.unamur.info.mdl.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.unamur.info.mdl.dal.entity.User;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.CredentialDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.exceptions.InvalidCredentialException;
import be.unamur.info.mdl.service.exceptions.RegistrationException;
import be.unamur.info.mdl.service.impl.UserServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl userService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder pwdEncoder;


  private Map<Long, User> mockUsers;


  // User1.password = user1_pwd
  private static final User MOCK_USER_1 = new User(123L, "user1",
      "user1_pwd", "user1@email.dom", null,
      null);
  // User1.password = user2_pwd
  private static final User MOCK_USER_2 = new User(456L, "user2",
      "$2a$10$HSIgcJ/ZSd6mIhAOB/6gGuZS6soHxCO6/FGboVGGoXsBwyq8Dq0Le",
      "user2@email.dom", null,null);

  {
    mockUsers = new HashMap<>(3);
    mockUsers.put(MOCK_USER_1.getId(), MOCK_USER_1);
    mockUsers.put(MOCK_USER_2.getId(), MOCK_USER_2);

  }


  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);

  }


  @Test(expected = RegistrationException.class)
  public void when_registerNullDTO_then_throwsException() throws RegistrationException {
    userService.register(null);
  }


  @Test(expected = RegistrationException.class)
  public void when_registerWithTakenEmail_then_throwsException() throws RegistrationException {
    UserDTO newUser = new UserDTO();
    newUser.setEmail(MOCK_USER_1.getEmail());

    when(userRepository.findByEmail(newUser.getEmail())).thenReturn(MOCK_USER_1);
    userService.register(newUser);
  }


  @Test(expected = RegistrationException.class)
  public void when_registerWithTakenUsername_then_throwsException() throws RegistrationException {
    UserDTO newUser = new UserDTO();
    newUser.setUsername(MOCK_USER_1.getUsername());

    when(userRepository.findByUsername(newUser.getUsername())).thenReturn(MOCK_USER_1);
    userService.register(newUser);
  }


  @Test(expected = Exception.class)
  public void when_register_ok() throws RegistrationException {
    String newUserPassword = "mock_password";
    UserDTO newUser = new UserDTO();
    newUser.setUsername(MOCK_USER_1.getUsername());
    newUser.setEmail(MOCK_USER_1.getEmail());
    newUser.setPassword(newUserPassword);

    when(userRepository.findByUsername(newUser.getUsername())).thenReturn(MOCK_USER_1);
    when(userRepository.save(any())).thenReturn(null);

    userService.register(newUser);

    verify(pwdEncoder).encode(newUser.getPassword());
    verify(userRepository).save(any());
  }

  @Test(expected = InvalidCredentialException.class)
  public void when_loginWitKnowUsernameNoPassword_then_throwsException()
      throws InvalidCredentialException {
    CredentialDTO user1 = new CredentialDTO();
    user1.setUsername("user1");

    when(userRepository.findByUsername("user1")).thenReturn(MOCK_USER_1);

    userService.login(user1);
    fail("The login should be rejected for empty password");
  }


  @Test(expected = InvalidCredentialException.class)
  public void when_loginWithUnknownUsername_then_throwsException()
      throws InvalidCredentialException {
    CredentialDTO user2 = new CredentialDTO();
    user2.setUsername("user");
    user2.setPassword("pwd");

    when(userRepository.findByUsername("user")).thenReturn(new User());

    userService.login(user2);
    verify(userRepository).findByUsername("user");
    fail("The login should be rejected for unknown user");
  }


  @Test(expected = InvalidCredentialException.class)
  public void when_loginWithNoPwd_then_throwsException() throws InvalidCredentialException {
    // Unkown username without password
    CredentialDTO user = new CredentialDTO();
    user.setUsername("user1");

    when(userRepository.findByUsername(MOCK_USER_1.getUsername())).thenReturn(MOCK_USER_1);

    userService.login(user);
    fail("The login should be rejected for no password");
  }

  @Test(expected = InvalidCredentialException.class)
  public void when_loginWithIncorrectPwd_then_throwsException() throws InvalidCredentialException {
    // Known user with incorrect password

    CredentialDTO user = new CredentialDTO();
    user.setUsername(MOCK_USER_1.getUsername());
    user.setPassword("pwd");

    when(userRepository.findByUsername(MOCK_USER_1.getUsername())).thenReturn(MOCK_USER_1);

    userService.login(user);
    fail("The login should be rejected for incorect pwd");
  }

  @Test
  public void when_login_ok() {
    // Unkown username without password
    CredentialDTO user = new CredentialDTO();
    user.setUsername("user2");
    user.setPassword("user2_pwd");

    when(userRepository.findByUsername("user2")).thenReturn(MOCK_USER_2);
    when(pwdEncoder.matches(anyString(),anyString())).thenReturn(true);

    try {
      String token = userService.login(user);
      assertNotNull("Should have received a valid token", token);
    } catch (InvalidCredentialException e) {
      fail("The login should be accepted for good credentials");
    }

  }


}

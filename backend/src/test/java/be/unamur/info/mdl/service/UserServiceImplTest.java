package be.unamur.info.mdl.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.unamur.info.mdl.dal.entity.User;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.CredentialDTO;
import be.unamur.info.mdl.dto.UserDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl userService;

  @Mock
  private BCryptPasswordEncoder pwdEncoder;

  @Mock
  private UserRepository userRepository;

  private Map<Long, User> mockUsers;


  // User1.password = user1_pwd
  private static final User MOCK_USER_1 = new User(123L, "user1",
      "user1_pwd", "user1@email.dom", null,
      null);
  // User1.password = user2_pwd
  private static final User MOCK_USER_2 = new User(456L, "user2",
      "user2_pwd", "user2@email.dom", null,
      null);

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

  @Test
  public void when_loginWitKnowUsernameNoPassword_thenReturnsFalse() {
    CredentialDTO user1 = new CredentialDTO();
    user1.setUsername("user1");

    when(userRepository.findByUsername("user1")).thenReturn(MOCK_USER_1);

    boolean isAuth = userService.login(user1);
    assertFalse("The login should be rejected for empty password", isAuth);
  }


  @Test
  public void when_loginWithUnknownUsername_thenReturnsFalse() {
    CredentialDTO user2 = new CredentialDTO();
    user2.setUsername("user");
    user2.setPassword("pwd");

    when(userRepository.findByUsername("user")).thenReturn(new User());

    boolean isAuth = userService.login(user2);
    verify(userRepository).findByUsername("user");
    assertFalse("The login should be rejected for unknown user", isAuth);
  }


  @Test
  public void when_loginWithNoPwd_thenReturnsFalse() {
    // Unkown username without password
    CredentialDTO user = new CredentialDTO();
    user.setUsername("user1");

    when(userRepository.findByUsername(MOCK_USER_1.getUsername())).thenReturn(MOCK_USER_1);

    boolean isAuth = userService.login(user);
    assertFalse("The login should be rejected for no password", isAuth);
  }

  @Test
  public void when_loginWithIncorrectPwd_thenReturnsFalse() {
    // Known user with incorrect password

    CredentialDTO user = new CredentialDTO();
    user.setUsername(MOCK_USER_1.getUsername());
    user.setPassword("pwd");

    when(userRepository.findByUsername(MOCK_USER_1.getUsername())).thenReturn(MOCK_USER_1);

    boolean isAuth = userService.login(user);
    assertFalse("The login should be rejected for incorect pwd", isAuth);
  }

  @Test
  public void when_login_ok() {
    // Unkown username without password
    CredentialDTO user = new CredentialDTO();
    user.setUsername("user1");
    user.setPassword("user1_pwd");

    when(userRepository.findByUsername("user1")).thenReturn(MOCK_USER_1);

    boolean isLogged = userService.login(user);
    assertTrue("The login should be accepted for good credentials", !isLogged);
  }


}

package be.unamur.info.mdl.service;

import static org.junit.Assert.assertFalse;

import be.unamur.info.mdl.dal.entity.User;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.impl.UserServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

  @Profile("test")
  @Configuration
  static public class TestConfiguration {

    @Bean(name = "userService")
    @Primary
    public UserService userService() {
      return Mockito.mock(UserServiceImpl.class);
    }
  }


  @Autowired
  private UserService userService;

  @MockBean
  private UserRepository userDAO;

  private Map<Long, User> mockUsers;
  User MOCK_USER_1 = new User(123L,"user1","user1_pwd","user1@email.dom",null,null);
  User MOCK_USER_2 = new User(456L,"user2","user2_pwd","user2@email.dom",null,null);


  @Before
  public void init(){
//    this.userService = new UserServiceImpl(userDAO);


    mockUsers = new HashMap<>(3);
    mockUsers.put(MOCK_USER_1.id(),MOCK_USER_1);
    mockUsers.put(MOCK_USER_2.id(),MOCK_USER_2);

    MockitoAnnotations.initMocks(this);
    Mockito.when(userDAO.save(MOCK_USER_1)).thenReturn(MOCK_USER_1);
    Mockito.when(userDAO.save(MOCK_USER_2)).thenReturn( MOCK_USER_2);

    Mockito.when(userDAO.findByUsername(MOCK_USER_1.username())).thenReturn(MOCK_USER_1);
    Mockito.when(userDAO.findByUsername(MOCK_USER_2.username())).thenReturn(MOCK_USER_2);
  }

  @Ignore
  @Test
  public void when_registerNullDTO_then_throwsException() {

  }

  @Ignore
  @Test(expected = Exception.class)
  public void when_registerWithTakenEmail_then_throwsException() {

  }

  @Ignore
  @Test(expected = Exception.class)
  public void when_registerWithTakenUsername_then_throwsException() {

  }

  @Ignore
  @Test(expected = Exception.class)
  public void when_register_ok() {

  }

  @Test
  public void when_loginWithUnknownUsername_thenReturnsFalse(){
    // Unkown username without password
    boolean isLogged = userService.login(new UserDTO().username("user"));
    assertFalse("The login should be rejected", isLogged);

    isLogged = userService.login(new UserDTO().username("user").password("pwd"));
    assertFalse("The login should be rejected", isLogged);
  }


  @Test
  public void when_loginWithIncorrectPwd_thenReturnsFalse(){
    // Unkown username without password
    boolean isLogged = userService.login(new UserDTO().username("user1"));
    assertFalse("The login should be rejected for no password", isLogged);

    isLogged = userService.login(new UserDTO().username(MOCK_USER_1.username()).password("pwd"));
    assertFalse("The login should be rejected for incorect pwd", isLogged);
  }



}

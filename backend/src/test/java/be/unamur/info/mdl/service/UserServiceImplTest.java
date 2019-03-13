package be.unamur.info.mdl.service;

import static org.junit.Assert.assertFalse;

import be.unamur.info.mdl.dal.entity.User;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.UserDTO;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

  @Autowired
  private UserService userService;

  @Mock
  private UserRepository userDAO;

  private Map<Long, User> mockUsers;

  @Before
  public void init(){
    MockitoAnnotations.initMocks(this);
    User u1 = new User(123L,"user1","user1_pwd","user1@email.dom",null,null);


    User u2 = new User(456L,"user2","user2_pwd","user2@email.dom",null,null);

    mockUsers = new HashMap<>(3);

    Mockito.when(userDAO.save(u1)).then((Answer<?>) mockUsers.put(123l,u1));
    Mockito.when(userDAO.save(u2)).then((Answer<?>) mockUsers.put(456l,u2));
  }

  @Test
  public void when_registerNullDTO_then_throwsException() {


  }

  @Test(expected = Exception.class)
  public void when_registerWithTakenEmail_then_throwsException() {

  }

  @Test(expected = Exception.class)
  public void when_registerWithTakenUsername_then_throwsException() {

  }


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

}

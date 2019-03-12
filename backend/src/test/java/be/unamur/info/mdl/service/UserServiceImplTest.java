package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.UserDTO;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

  @InjectMocks
  private UserService userService;

  @Mock
  private UserRepository userDAO;

  private Map<Long, UserDTO> mockUsers;

  @Before
  public void init(){
    MockitoAnnotations.initMocks(this);
    UserDTO u1 = null;
    UserDTO u2 = null;

//    Mockito.when(userDAO.save(u1)).then(mockUsers.put(1l,u1));
//    Mockito.when(userDAO.save(u2)).then(mockUsers.put(1l,u2));
  }

  @Test
  public void test_registerNullDTO_then_throwsException() {

  }

  @Test
  public void test_registerWithTakenEmail_then_throwsException() {

  }

  @Test
  public void test_registerWithTakenUsername_then_throwsException() {

  }


  @Test
  public void test_register_ok() {

  }


}

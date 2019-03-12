package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dal.entity.User;
import be.unamur.info.mdl.dal.repository.UserRepository;
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
public class UserServiceImpTest {

  @InjectMocks
  private UserService userService;

  @Mock
  private UserRepository userDAO;

  private Map<Long, User> mockUsers;

  @Before
  public void init(){
    MockitoAnnotations.initMocks(this);
    User u1;
    User u2;
    Mockito.when(userDAO.save(u1)).then(mockUsers.put(1l,u1));
    Mockito.when(userDAO.save(u2)).then(mockUsers.put(1l,u2));
  }

  @Test
  public void testAddNewUser() {

  }

}

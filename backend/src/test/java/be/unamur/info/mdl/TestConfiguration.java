package be.unamur.info.mdl;

import be.unamur.info.mdl.service.UserService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TestConfiguration {

  @Bean(name = "userService")
  @Primary
  public UserService userService() {
    return Mockito.mock(UserService.class);
  }
}

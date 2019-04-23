package be.unamur.info.mdl.config;

import be.unamur.info.mdl.dal.repository.UserRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
@Import({ApplicationConfiguration.class})
public class ApplicationTestConfiguration {

  @Bean
  @Primary
  public UserRepository userRepository() {
    return Mockito.mock(UserRepository.class);
  }

}

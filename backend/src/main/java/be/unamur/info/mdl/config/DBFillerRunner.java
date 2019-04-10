package be.unamur.info.mdl.config;

import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.ArticleService;
import be.unamur.info.mdl.service.UserService;
import be.unamur.info.mdl.service.exceptions.ArticleAlreadyExistException;
import be.unamur.info.mdl.service.exceptions.RegistrationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties.Registration;

public class DBFillerRunner implements CommandLineRunner {

  private static final Logger LOGGER = LoggerFactory.getLogger(DBFillerRunner.class);

  private final UserService userService;
  private final ArticleService articleService;



  public DBFillerRunner(UserService userService,
    ArticleService articleService) {
    this.userService = userService;
    this.articleService = articleService;
  }


  @Override
  public void run(String... args) throws Exception {
    ObjectMapper mapper;
    InputStream inputStream;

    // 1- Register some users

    mapper = new ObjectMapper();
    TypeReference<List<UserDTO>> userTypeReference = new TypeReference<List<UserDTO>>() {
    };
    InputStream usersInputStream = TypeReference.class.getResourceAsStream("/json/users.data.json");
    List<UserDTO> users = mapper.readValue(usersInputStream, userTypeReference);

    LOGGER.info("Save " + users.size() + " users into DB");

    for (UserDTO user : users) {
      try {
        this.userService.signin(user);
      } catch (RegistrationException e) {
        LOGGER.info(String.format("User (%s) : Already registred !", user.getUsername()));
      }
    }

    // 2- Create some related article

    TypeReference<List<ArticleDTO>> articleTypeReference = new TypeReference<List<ArticleDTO>>() {
    };
    inputStream = TypeReference.class.getResourceAsStream("/json/users.data.json");
    List<ArticleDTO> articles = mapper.readValue(inputStream, articleTypeReference);

    UserDTO authUser;
    for (ArticleDTO article : articles) {
      int userIndex = Math.random() < 0.5 ? 0 : 1;
      authUser = users.get(userIndex);
      try {
        this.articleService.create(article, authUser);
      } catch (ArticleAlreadyExistException e) {
        LOGGER.info(String.format("Article # %s : Already registred !", article.getReference()));
      }
    }

  }

}

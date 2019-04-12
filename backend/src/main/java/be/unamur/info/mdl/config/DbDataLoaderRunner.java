package be.unamur.info.mdl.config;

import be.unamur.info.mdl.dal.entity.TagEntity;
import be.unamur.info.mdl.dal.repository.TagRepository;
import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.ArticleService;
import be.unamur.info.mdl.service.UserService;
import be.unamur.info.mdl.service.exceptions.ArticleAlreadyExistException;
import be.unamur.info.mdl.service.exceptions.RegistrationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.slugify.Slugify;
import java.io.InputStream;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

public class DbDataLoaderRunner implements CommandLineRunner {

  private static final Logger LOGGER = LoggerFactory.getLogger(DbDataLoaderRunner.class);

  private final UserService userService;
  private final ArticleService articleService;
  private final TagRepository tagRepository;


  public DbDataLoaderRunner(UserService userService,
    ArticleService articleService,
    TagRepository tagRepository) {
    this.userService = userService;
    this.articleService = articleService;
    this.tagRepository = tagRepository;
  }


  @Override
  public void run(String... args) throws Exception {
    ObjectMapper mapper = new ObjectMapper()
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    InputStream inputStream;


    // 1- Register some users

    TypeReference<List<UserDTO>> userTypeReference = new TypeReference<List<UserDTO>>() {
    };
    inputStream = TypeReference.class.getResourceAsStream("/json/users.data.json");
    List<UserDTO> users = mapper.readValue(inputStream, userTypeReference);

    LOGGER.info("Save " + users.size() + " users into DB");

    for (UserDTO user : users) {
      try {
        this.userService.signin(user);
      } catch (RegistrationException e) {
        LOGGER.info(String.format("User (%s) : Already registred !", user.getUsername()));
      }
    }

    // 2- Create some categories && tags

    TypeReference<List<String>> dtoTypeReference = new TypeReference<List<String>>() {
    };
    inputStream = TypeReference.class.getResourceAsStream("/json/categories.data.json");
    List<String> list = mapper.readValue(inputStream, dtoTypeReference);

    LOGGER.info("Save " + list.size() + " categories into DB");

    Slugify slugify = new Slugify();
    for (String name : list) {
      try {
        String slug = slugify.slugify(name);
        TagEntity tag = TagEntity.builder().name(name).slug(slug).build();
        this.tagRepository.save(tag);
      } catch (Exception e) {
        LOGGER.info(String.format("Category/Tag (%s) : Already registred !", name));
      }
    }

    // 3- Create some related article

    TypeReference<List<ArticleDTO>> articleTypeReference = new TypeReference<List<ArticleDTO>>() {
    };
    inputStream = TypeReference.class.getResourceAsStream("/json/articles.data.json");
    List<ArticleDTO> articles = mapper.readValue(inputStream, articleTypeReference);

    LOGGER.info("Save " + articles.size() + " articles into DB");

    UserDTO authUser;
    for (ArticleDTO article : articles) {
      int userIndex = Math.random() < 0.5 ? 0 : 1;
      authUser = users.get(userIndex);
      try {
        this.articleService.create(article, authUser);
        LOGGER.info(String.format("Article %s : registred !", article.getReference()));
      } catch (ArticleAlreadyExistException e) {
        LOGGER.info(String.format("Article %s : Already registred !", article.getReference()));
      }
    }

    LOGGER.info("DB data loaded");
  }

}

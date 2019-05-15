package be.unamur.info.mdl.config;

import be.unamur.info.mdl.dal.entity.ResearchGroupEntity;
import be.unamur.info.mdl.dal.entity.TagEntity;
import be.unamur.info.mdl.dal.entity.UniversityEntity;
import be.unamur.info.mdl.dal.repository.ResearchGroupRepository;
import be.unamur.info.mdl.dal.repository.TagRepository;
import be.unamur.info.mdl.dal.repository.UniversityRepository;
import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.ResearchGroupDTO;
import be.unamur.info.mdl.dto.StateOfTheArtDTO;
import be.unamur.info.mdl.dto.UniversityInfoDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.exceptions.ArticleAlreadyExistException;
import be.unamur.info.mdl.exceptions.RegistrationException;
import be.unamur.info.mdl.exceptions.SotaAlreadyExistException;
import be.unamur.info.mdl.service.ArticleService;
import be.unamur.info.mdl.service.StateOfTheArtService;
import be.unamur.info.mdl.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.slugify.Slugify;
import java.io.InputStream;
import java.util.List;
import org.h2.message.DbException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class DbDataLoaderRunner implements CommandLineRunner {

  private static final Logger LOGGER = LoggerFactory.getLogger(DbDataLoaderRunner.class);

  @Autowired
  private UserService userService;

  @Autowired
  private ArticleService articleService;

  @Autowired
  private TagRepository tagRepository;

  @Autowired
  private StateOfTheArtService sotaService;

  @Autowired
  private UniversityRepository universityRepository;
  @Autowired
  private ResearchGroupRepository researchGroupRepository;




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

    LOGGER.info("Saving " + users.size() + " users into DB");

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
    List<String> categories = mapper.readValue(inputStream, dtoTypeReference);

    LOGGER.info("Saving " + categories.size() + " categories into DB");

    Slugify slugify = new Slugify();
    for (String name : categories) {
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

    LOGGER.info("Saving " + articles.size() + " articles into DB");

    UserDTO authUser;
    for (ArticleDTO article : articles) {
      int userIndex = Math.random() < 0.5 ? 0 : 1;
      authUser = users.get(userIndex);
      try {
        article.setContent(article.getTitle());
        this.articleService.create(article, authUser);
        LOGGER.info(String.format("Article %s : registred !", article.getReference()));
      } catch (ArticleAlreadyExistException e) {
        LOGGER.info(String.format("Article %s : Already registred !", article.getReference()));
      } catch (DbException e) {
        LOGGER.info(" Sota insert failed : " + article.getReference());
      }
    }

    // 4- Create some related sotas

    TypeReference<List<StateOfTheArtDTO>> sotaTypeReference = new TypeReference<List<StateOfTheArtDTO>>() {
    };
    inputStream = TypeReference.class.getResourceAsStream("/json/sotas.data.json");
    List<StateOfTheArtDTO> sotas = mapper.readValue(inputStream, sotaTypeReference);

    LOGGER.info("Saving " + sotas.size() + " sotas into DB");

    for (StateOfTheArtDTO sota : sotas) {
      int userIndex = Math.random() < 0.5 ? 0 : 1;
      authUser = users.get(userIndex);
      try {
        StateOfTheArtDTO dbSota = this.sotaService.create(sota, authUser);
        LOGGER.info(String.format("Sota %s : registred !", dbSota.getReference()));
      } catch (SotaAlreadyExistException e) {
        LOGGER.info(String.format("Sota %s : Already registred !", sota.getReference()));
      } catch (ArticleAlreadyExistException e) {
        LOGGER.info(String.format(e.getMessage()));
      } catch (DbException e) {
        LOGGER.info(" Sota insert failed : " + sota.getTitle());
      }
    }

    LOGGER.info("DB data loaded");

    TypeReference<List<UniversityInfoDTO>> universityTypeReference = new TypeReference<List<UniversityInfoDTO>>() {
    };
    inputStream = TypeReference.class.getResourceAsStream("/json/university.data.json");
    List<UniversityInfoDTO> universities = mapper.readValue(inputStream, universityTypeReference);
    LOGGER.info("Saving " + universities.size() + " universities into DB");

    for (UniversityInfoDTO university : universities) {
      this.universityRepository.save(UniversityEntity.of(university));
    }

    TypeReference<List<String>> domainTypeReference = new TypeReference<List<String>>() {
    };
    inputStream = TypeReference.class.getResourceAsStream("/json/domain.data.json");
    List<String> domain = mapper.readValue(inputStream, domainTypeReference);

    LOGGER.info("Saving " + domain.size() + " added into DB");


    /*for (String name : domain) {
      try {
        String slug = slugify.slugify(name);
        TagEntity tag = TagEntity.builder().name(name).slug(slug).build();
        this.tagRepository.save(tag);
      } catch (Exception e) {
        LOGGER.info(String.format("Category/Tag (%s) :Doublon Detected !", name));
      }
    }
*/
    TypeReference<List<ResearchGroupDTO>> researchGroupTypeReference = new TypeReference<List<ResearchGroupDTO>>() {
    };
    inputStream = TypeReference.class.getResourceAsStream("/json/researchGroup.data.json");
    List<ResearchGroupDTO> groups = mapper.readValue(inputStream, researchGroupTypeReference);

    LOGGER.info("Saving " + groups.size() + " articles into DB");

    for (ResearchGroupDTO group : groups) {
      try {

     this.researchGroupRepository.save(ResearchGroupEntity.of(group));
        LOGGER.info("add " + group.getName() + " from ResearchGroup DB");
      }
     catch(Exception e ){
       LOGGER.info(" YOU FAILED");
     }

      }
    }
  }






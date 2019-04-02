package be.unamur.info.mdl.service;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.unamur.info.mdl.dal.entity.ArticleEntity;
import be.unamur.info.mdl.dal.entity.UserEntity;
import be.unamur.info.mdl.dal.repository.ArticleRepository;
import be.unamur.info.mdl.dal.repository.StateOfTheArtRepository;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.CredentialDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.exceptions.InvalidCredentialException;
import be.unamur.info.mdl.service.exceptions.RegistrationException;
import be.unamur.info.mdl.service.impl.SearchServiceImpl;
import be.unamur.info.mdl.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class SearchServiceImplTest {

  @InjectMocks
  private SearchServiceImpl searchService;

  @Mock
  private ArticleRepository articleRepository;

  @Mock
  private StateOfTheArtRepository satoRepository;


  private static final ArticleEntity MOCK_ARTICLE_1 = new ArticleEntity();

  private static final ArticleEntity MOCK_ARTICLE_2 = new ArticleEntity();


  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);

  }


}

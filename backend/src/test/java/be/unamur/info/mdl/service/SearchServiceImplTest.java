package be.unamur.info.mdl.service;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import be.unamur.info.mdl.dal.entity.ArticleEntity;
import be.unamur.info.mdl.dal.repository.ArticleRepository;
import be.unamur.info.mdl.dal.repository.StateOfTheArtRepository;
import be.unamur.info.mdl.service.impl.SearchServiceImpl;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
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

package be.unamur.info.mdl.service;

import static org.mockito.Mockito.when;

import be.unamur.info.mdl.dal.entity.ArticleEntity;
import be.unamur.info.mdl.dal.entity.TagEntity;
import be.unamur.info.mdl.dal.entity.TagEntity.TagEntityBuilder;
import be.unamur.info.mdl.dal.repository.ArticleRepository;
import be.unamur.info.mdl.dal.repository.AuthorRepository;
import be.unamur.info.mdl.dal.repository.StateOfTheArtRepository;
import be.unamur.info.mdl.dal.repository.TagRepository;
import be.unamur.info.mdl.dal.repository.UserRepository;
import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.service.exceptions.ArticleNotFoundException;
import be.unamur.info.mdl.service.impl.ArticleServiceImpl;
import be.unamur.info.mdl.service.impl.SearchServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceImplTest {

  @InjectMocks
  private ArticleServiceImpl articleService;

  @Mock
  private ArticleRepository articleRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private TagRepository tagRepository;

  @Mock
  private AuthorRepository authorRepository;


  public static ArticleEntity MOCK_ARTICLE_1;

  public static ArticleEntity MOCK_ARTICLE_2;


  @BeforeClass
  public static void startup() throws IOException {
    ObjectMapper mapper = new ObjectMapper()
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    TypeReference<List<ArticleDTO>> articleTypeReference = new TypeReference<List<ArticleDTO>>() {
    };
    InputStream inputStream = TypeReference.class
      .getResourceAsStream("/json/articles.data.json");
    List<ArticleDTO> articles = mapper.readValue(inputStream, articleTypeReference);

    ArticleDTO articleData = articles.get(0);
    MOCK_ARTICLE_1 = ArticleEntity.of(articleData);
    TagEntity category = TagEntity.builder().name(articleData.getCategory()).build();
    MOCK_ARTICLE_1.setCategory(category);
    MOCK_ARTICLE_1.setCreator(UserServiceImplTest.MOCK_USER_1);

    articleData = articles.get(1);
    MOCK_ARTICLE_2 = ArticleEntity.of(articleData);
    category = TagEntity.builder().name(articleData.getCategory()).build();
    MOCK_ARTICLE_2.setCategory(category);
    MOCK_ARTICLE_2.setCategory(new TagEntity());
    MOCK_ARTICLE_2.setCreator(UserServiceImplTest.MOCK_USER_2);
  }


  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);

    when(articleRepository.findByReference("")).thenReturn(Optional.empty());
    when(articleRepository.findByReference("article123")).thenReturn(Optional.empty());
    when(articleRepository.findByReference("rebolj1999gis"))
      .thenReturn(Optional.of(MOCK_ARTICLE_1));

  }


  @Test(expected = IllegalArgumentException.class)
  public void when_getNullReference_then_throwsException() {
    articleService.getArticleByReference(null);
  }

  @Test(expected = ArticleNotFoundException.class)
  public void when_getEmptyReference_then_throwsException() {
    articleService.getArticleByReference("");
  }


  @Test(expected = ArticleNotFoundException.class)
  public void when_getInvalidReference_then_throwsException() {
    articleService.getArticleByReference("article123");
  }

  @Test
  public void when_getValidReference_then_ok() {
    ArticleDTO article = articleService.getArticleByReference("rebolj1999gis");

    Assert.assertNotNull("The article should not be null", article);
    Assert.assertSame("The reference should match", article.getReference(),
      MOCK_ARTICLE_1.getReference());
  }


}

package be.unamur.info.mdl.ctrler;

import static be.unamur.info.mdl.service.ArticleServiceImplTest.MOCK_ARTICLE_1;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import be.unamur.info.mdl.service.ArticleServiceImplTest;
import be.unamur.info.mdl.service.exceptions.ArticleNotFoundException;
import be.unamur.info.mdl.service.impl.ArticleServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SotaControllerTest {

  @Autowired
  private MockMvc api;

  @MockBean
  private ArticleServiceImpl articleService;

  private static final String GET_SOTA_URL = "/api/sota";


  @BeforeClass
  public static void startup() throws Exception {
    ArticleServiceImplTest.startup();
  }


  @Test
  public void get_article_without_reference() throws Exception {
    api.perform(MockMvcRequestBuilders.get(GET_SOTA_URL + "/")
      .accept(MediaType.APPLICATION_JSON)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is4xxClientError());
  }

  @Test
  public void get_article_with_invalid_reference() throws Exception {
    when(articleService.getArticleByReference(anyString()))
      .thenThrow(new ArticleNotFoundException(""));

    api.perform(MockMvcRequestBuilders.get(GET_SOTA_URL + "/invalid")
      .accept(MediaType.APPLICATION_JSON)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());

    api.perform(MockMvcRequestBuilders.get(GET_SOTA_URL + "/article123")
      .accept(MediaType.APPLICATION_JSON)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
  }

  @Test
  public void get_article_with_valid_reference() throws Exception {
    when(articleService.getArticleByReference("rebolj1999gis"))
      .thenReturn((MOCK_ARTICLE_1.toDTO()));

    api.perform(MockMvcRequestBuilders.get(GET_SOTA_URL + "/rebolj1999gis")
      .accept(MediaType.APPLICATION_JSON)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.reference").isNotEmpty())
      .andExpect(jsonPath("$.title").isNotEmpty())
      .andExpect(jsonPath("$.content").isNotEmpty())
      .andExpect(jsonPath("$.url").isNotEmpty())
      .andExpect(jsonPath("$.journal").isNotEmpty())
      .andExpect(jsonPath("$.volume").hasJsonPath())
      .andExpect(jsonPath("$.publisher").hasJsonPath())
      .andExpect(jsonPath("$.year").isNumber())
      .andExpect(jsonPath("$.month").hasJsonPath())
      .andExpect(jsonPath("$.price").isNumber())
      .andExpect(jsonPath("$.pages").isNotEmpty())
      .andExpect(jsonPath("$.category").isNotEmpty())
      .andExpect(jsonPath("$.creator").isMap())
      .andExpect(jsonPath("$.authors").isArray())
      .andExpect(jsonPath("$.keywords").isArray())


    ;


  }
}

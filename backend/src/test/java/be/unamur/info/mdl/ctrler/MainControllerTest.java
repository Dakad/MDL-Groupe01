package be.unamur.info.mdl.ctrler;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import be.unamur.info.mdl.dto.CredentialDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

  @Autowired
  private MockMvc api;

  private static final String LOGIN_URL = "/api/login";


  @Test
  public void login_with_null_credentials() throws Exception {
    api.perform(MockMvcRequestBuilders.request(HttpMethod.POST, LOGIN_URL, new CredentialDTO())
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void login_with_empty_credentials() throws Exception {
    CredentialDTO credential = new CredentialDTO("", "");
    api.perform(MockMvcRequestBuilders.request(HttpMethod.POST, LOGIN_URL, credential)
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
//        .andExpect(content().json("Yello Greetings from Spring Boot!"))
    ;

    CredentialDTO credential2 = new CredentialDTO("correct_username", "");
    api.perform(MockMvcRequestBuilders.request(HttpMethod.POST, LOGIN_URL, credential2)
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    CredentialDTO credential3 = new CredentialDTO("", "correct_pwd");
    api.perform(MockMvcRequestBuilders.request(HttpMethod.POST, LOGIN_URL, credential3)
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }


  @Test
  public void login_with__credentials() throws Exception {
    CredentialDTO credential = new CredentialDTO("", "");
    api.perform(MockMvcRequestBuilders.request(HttpMethod.POST, LOGIN_URL, credential)
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
//        .andExpect(content().json("Yello Greetings from Spring Boot!"))
    ;
  }



}

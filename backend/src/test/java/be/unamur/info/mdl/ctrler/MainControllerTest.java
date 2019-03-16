package be.unamur.info.mdl.ctrler;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import be.unamur.info.mdl.dto.CredentialDTO;
import org.hamcrest.core.Is;
import org.json.JSONObject;
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
    JSONObject credential = new JSONObject();
    credential.put("username","");
    credential.put("password", "");
    api.perform(MockMvcRequestBuilders.post(LOGIN_URL)
        .content(credential.toString())
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
//        .andExpect(content().json("Yello Greetings from Spring Boot!"))
    ;

    JSONObject credential2 = new JSONObject();
    credential.put("username","correct_username");
    credential.put("password", "");

    api.perform(MockMvcRequestBuilders.post(LOGIN_URL)
        .content(credential2.toString())
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
//        .andExpect(jsonPath("$.validation.password", Is.is("The username cannot be empty or blank")))
    ;


    JSONObject credential3 = new JSONObject();
    credential.put("username","");
    credential.put("password", "correct_password");
    api.perform(MockMvcRequestBuilders.post(LOGIN_URL)
        .content(credential3.toString())
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
//        .andExpect(jsonPath("$.validation.username", Is.is("The password cannot be empty or blank")))
    ;
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


  @Test
  public void login_with_unknown_credentials() throws Exception {
    CredentialDTO credential = new CredentialDTO("no_user", "");
    api.perform(MockMvcRequestBuilders.request(HttpMethod.POST, LOGIN_URL, credential)
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
//        .andExpect(content().json("Yello Greetings from Spring Boot!"))
    ;
  }





}

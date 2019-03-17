package be.unamur.info.mdl.ctrler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import be.unamur.info.mdl.config.ApplicationTestConfiguration;
import be.unamur.info.mdl.dto.CredentialDTO;
import be.unamur.info.mdl.service.exceptions.InvalidCredentialException;
import be.unamur.info.mdl.service.impl.UserServiceImpl;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

  @Autowired
  private MockMvc api;

  @MockBean
  private UserServiceImpl userService;


  private static final String LOGIN_URL = "/api/login";


  @Test
  public void login_with_null_credentials() throws Exception {
    JSONObject credential = new JSONObject();
    api.perform(MockMvcRequestBuilders.request(HttpMethod.POST, LOGIN_URL, credential)
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
  public void login_with_unknown_credentials() throws Exception {
    JSONObject credential = new JSONObject();
    credential.put("username","no_user");
    credential.put("password", "no_password");

    api.perform(MockMvcRequestBuilders.post(LOGIN_URL)
        .content(credential.toString())
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
//        .andExpect(content().json("Yello Greetings from Spring Boot!"))
    ;
  }


  @Test
  public void login_with_invalid_credentials() throws Exception {
    JSONObject credential = new JSONObject();
    credential.put("username","invalid_user_name");
    credential.put("password", "invalid_Pwd_123");

    when(userService.login(any())).thenThrow(InvalidCredentialException.class);

    api.perform(MockMvcRequestBuilders.post(LOGIN_URL)
        .content(credential.toString())
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict())
    ;
  }

  @Test
  public void login_with_credentials() throws Exception {
    JSONObject credential = new JSONObject();
    credential.put("username","correct_user_name");
    credential.put("password", "correct_Pwd_123");

    when(userService.login(any())).thenReturn("JWT_TEST_TOKEN");

    api.perform(MockMvcRequestBuilders.post(LOGIN_URL)
        .content(credential.toString())
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.auth_token").exists())
        .andExpect(jsonPath("$.auth_token").value("JWT_TEST_TOKEN"))
    ;
  }





}

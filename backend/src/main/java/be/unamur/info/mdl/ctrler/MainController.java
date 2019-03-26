package be.unamur.info.mdl.ctrler;


import be.unamur.info.mdl.dto.CredentialDTO;
import be.unamur.info.mdl.dto.SearchQueryDTO;
import be.unamur.info.mdl.dto.SearchResultDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.SearchService;
import be.unamur.info.mdl.service.UserService;
import be.unamur.info.mdl.service.exceptions.InvalidCredentialException;
import be.unamur.info.mdl.service.exceptions.RegistrationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

// import org.springframework.stereotype.Controller;


@RestController
@RequestMapping(path = "/api")
public class MainController {

  @Autowired
  private UserService userService;
  private SearchService searchService;


  @RequestMapping(path = "/signin", method = RequestMethod.POST)
  public ResponseEntity<Map<String,String>> signin(@Valid @RequestBody UserDTO userData) {
    Map<String,String> response = new HashMap<>();
    try {
      this.userService.signin(userData);
      response.put("success", "New user registred");
      return new ResponseEntity(response, HttpStatus.CREATED);
    } catch (RegistrationException ex) {
      response.put("error", ex.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

  }


  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @Validated
  public ResponseEntity<Map<String,String>> login(@Valid @RequestBody CredentialDTO userDTO) {
    Map<String,String> result = new HashMap<>();
    try {
      String token = userService.login(userDTO);
      result.put("auth_token", token);
      return ResponseEntity.status(HttpStatus.OK).body(result);
    } catch (InvalidCredentialException ex) {
      result.put("auth_token", ex.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
  }

  @RequestMapping(value = "/search?p={page}&o={order}&s={sort}", method = RequestMethod.GET)
  public ResponseEntity<SearchResultDTO> search(@Valid @RequestParam SearchQueryDTO searchQuerry) {
    SearchResultDTO resultDTO = searchService.getSearchResults(searchQuerry);
    return ResponseEntity.status(HttpStatus.OK).body(resultDTO);
  }

}

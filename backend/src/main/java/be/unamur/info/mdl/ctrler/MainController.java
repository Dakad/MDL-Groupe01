package be.unamur.info.mdl.ctrler;


import be.unamur.info.mdl.dto.CredentialDTO;
import be.unamur.info.mdl.dto.SearchQueryDTO;
import be.unamur.info.mdl.dto.SearchResultDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.SearchService;
import be.unamur.info.mdl.service.UserService;
import be.unamur.info.mdl.service.exceptions.InvalidCredentialException;
import be.unamur.info.mdl.service.exceptions.RegistrationException;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api")
public class MainController extends APIBaseController {

  @Autowired
  private UserService userService;
  @Autowired
  private SearchService searchService;


  @GetMapping(value = {"", "/zen"})
  public String yello() {
    return "Yello from MDL API !";
  }


  @RequestMapping(path = "/signin", method = RequestMethod.POST)
  public ResponseEntity<Map<String, String>> signin(@Valid @RequestBody UserDTO userData) {
    Map<String, String> response = new HashMap<>();
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
  public ResponseEntity<Map<String, String>> login(@Valid @RequestBody CredentialDTO userDTO) {
    Map<String, String> result = new HashMap<>();
    try {
      String token = userService.login(userDTO);
      result.put("auth_token", token);
      return ResponseEntity.status(HttpStatus.OK).body(result);
    } catch (InvalidCredentialException ex) {
      result.put("error", ex.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
  }

  @RequestMapping(value = "/search", method = RequestMethod.GET)//?p={page}&o={order}&s={sort}&k={keyword}&t={tag}
  public ResponseEntity<SearchResultDTO> search(
      @RequestParam(defaultValue = "0", required = false) Integer p,
      @RequestParam(defaultValue = "ASC", required = false) String o,
      @RequestParam(defaultValue = "DATE", required = false) String s,
      @RequestParam String k,
      @RequestParam(required = false) String t) {

    if(p<0) p = 0;
    SearchQueryDTO searchQuery = new SearchQueryDTO(k,t,p,o,s);
    SearchResultDTO resultDTO = searchService.getSearchResults(searchQuery);
    return ResponseEntity.status(HttpStatus.OK).body(resultDTO);
  }


}

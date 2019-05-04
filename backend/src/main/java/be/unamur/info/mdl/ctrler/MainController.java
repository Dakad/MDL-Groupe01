package be.unamur.info.mdl.ctrler;


import be.unamur.info.mdl.config.security.SecurityUtils;
import be.unamur.info.mdl.dto.CredentialDTO;
import be.unamur.info.mdl.dto.SearchQueryDTO;
import be.unamur.info.mdl.dto.SearchResultDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.SearchService;
import be.unamur.info.mdl.service.UserService;
import be.unamur.info.mdl.service.exceptions.InvalidCredentialException;
import be.unamur.info.mdl.service.exceptions.RegistrationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api")
@Api(value = "Main endpoints", description = "Operations unrelated to specific actions ")
public class MainController extends APIBaseController {

  @Autowired
  private UserService userService;

  @Autowired
  private SearchService searchService;


  @ApiOperation(value = "Ping endpoint to ensure, the API is effectively online", response = String.class)
  @ApiResponse(code = 200, message = "Simple message from the API")
  @GetMapping(path = {"", "/zen"})
  public String yello() {
    return "Yello from MDL API !";
  }


  @ApiOperation(value = "Retrieve the list of team members", response = List.class)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "List of each person with it avatar, full name, role and short description"),
    @ApiResponse(code = 500, message = "If some shit hit the fan :-)")
  })
  @RequestMapping(path = "/team", method = RequestMethod.GET)
  public ResponseEntity<List<Object>> getTeamMembers() {
    try {
      ObjectMapper mapper = new ObjectMapper();
      TypeReference<List<Object>> typeReference = new TypeReference<List<Object>>() {
      };
      InputStream inputStream = TypeReference.class.getResourceAsStream("/json/team.json");

      List<Object> members = mapper.readValue(inputStream, typeReference);
      return ResponseEntity.ok(members);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
    }
  }


  @ApiOperation(value = "Registration", response = ResponseEntity.class)
  @ApiResponses(value = {
    @ApiResponse(code = 201, message = "Successfully registered user"),
    @ApiResponse(code = 400, message = "Some required fields are invalid"),
    @ApiResponse(code = 409, message = "If the username or email is already taken")
  })
  @RequestMapping(path = "/signin", method = RequestMethod.POST)
  public ResponseEntity<Map<String, String>> signin(@Valid @RequestBody UserDTO userData) {
    Map<String, String> response = new HashMap<>();
    try {
      this.userService.signin(userData);
      response.put("success", "New user registered");
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } catch (RegistrationException ex) {
      response.put("error", ex.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
  }


  @ApiOperation(value = "Authentication", response = ResponseEntity.class)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully authenticated user", responseHeaders = {
      @ResponseHeader(name = SecurityUtils.HEADER_STRING, description = "Hold the Token with the prefix"),
    }),
    @ApiResponse(code = 400, message = "Some required fields are invalid"),
    @ApiResponse(code = 409, message = "If the username or password is not recognized")
  })
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity<Map<String, String>> login(
    @ApiParam(value = "Credentials", required = true)
    @Valid @RequestBody CredentialDTO userDTO) {
    Map<String, String> result = new HashMap<>();
    try {
      String token = userService.login(userDTO);
      HttpHeaders header = new HttpHeaders();
      header.set(SecurityUtils.HEADER_STRING, SecurityUtils.TOKEN_PREFIX + token);

      result.put("auth_token", token);
      result.put("auth_header", SecurityUtils.HEADER_STRING);
      result.put("auth_token_type", SecurityUtils.TOKEN_PREFIX);

      return ResponseEntity.status(HttpStatus.OK).headers(header).body(result);
    } catch (InvalidCredentialException ex) {
      result.put("error", ex.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
  }


  //?st={term}&p={page}&o={order}&s={sort}&t={tag}
  @ApiOperation(value = "Search articles, S.O.T.A or authors", response = SearchResultDTO.class)
  @ApiResponses(value = {
    @ApiResponse(code = 400, message = "Some required fields are invalid or missing"),
    @ApiResponse(code = 200, message = "List of each searched elements")
  })
  @RequestMapping(value = "/search", method = RequestMethod.GET)
  public ResponseEntity<SearchResultDTO> search(@Valid SearchQueryDTO query) {
    SearchResultDTO resultDTO = searchService.getSearchResults(query);
    return ResponseEntity.status(HttpStatus.OK).body(resultDTO);
  }


}

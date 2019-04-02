package be.unamur.info.mdl.ctrler;


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
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(value = "main_controller", description = "Operations of MainControler")
public class MainController extends APIBaseController {

  @Autowired
  private UserService userService;

  @Autowired
  private SearchService searchService;


  @ApiOperation(value = "Ping endpoint to ensure, the API is effectively online", response = String.class)
  @ApiResponse(code = 200, message = "Simple message from the API")
  @GetMapping(value = {"", "/zen"})
  public String yello() {
    return "Yello from MDL API !";
  }


  @ApiOperation(value = "Retrieve theth list of team members", response = List.class)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "List of each person with it avatar, fullname, role and short description"),
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


  @ApiOperation(value = "Inscription", response = ResponseEntity.class)
  @ApiResponses(value = {
    @ApiResponse(code = 201, message = "Successfully registred user"),
    @ApiResponse(code = 400, message = "Some required fields are invalid"),
    @ApiResponse(code = 409, message = "If the username or email is already taken")
  })
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


  @ApiOperation(value = "Connexion", response = ResponseEntity.class)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully authenticated user"),
    @ApiResponse(code = 400, message = "Some required fields are invalid"),
    @ApiResponse(code = 409, message = "If the username or password is not recognized")
  })
  @RequestMapping(value = "/login", method = RequestMethod.POST)
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


  //?p={page}&o={order}&s={sort}&k={keyword}&t={tag}
  @ApiOperation(value = "Search articles, S.O.T.A or authors", response = SearchResultDTO.class)
  @ApiResponse(code = 200, message = "List of each searched category")
  @RequestMapping(value = "/search", method = RequestMethod.GET)
  public ResponseEntity<SearchResultDTO> search(
    @ApiParam(value = "Pagination", defaultValue = "0")
    @RequestParam(defaultValue = "0", required = false) String p,

    @ApiParam(value = "Order", defaultValue = "ASC")
    @RequestParam(defaultValue = "ASC", required = false) String o,

    @ApiParam(value = "Sort", allowMultiple = true, defaultValue = "DATE")
    @RequestParam(defaultValue = "DATE", required = false) String s,

    @ApiParam(value = "Kewords", required = true)
    @RequestParam String k,

    @ApiParam(value = "Tags")
    @RequestParam(required = false) String t) {

    int page;
    try {
      page = Integer.parseInt(p);
    } catch (NumberFormatException e) {
      page = 0;
    }

    if (page < 0) {
      page = 0;
    }
    SearchQueryDTO searchQuery = new SearchQueryDTO(k, t, page, o, s);
    SearchResultDTO resultDTO = searchService.getSearchResults(searchQuery);
    return ResponseEntity.status(HttpStatus.OK).body(resultDTO);
  }


}

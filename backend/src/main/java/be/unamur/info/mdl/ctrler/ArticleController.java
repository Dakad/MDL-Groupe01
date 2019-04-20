package be.unamur.info.mdl.ctrler;

import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.ArticleService;
import be.unamur.info.mdl.service.exceptions.ArticleAlreadyExistException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/article")
@Api(value = "Article endpoints", description = "Operations related to an Artcle such as add, get, ...")
public class ArticleController extends APIBaseController {

  @Autowired
  private ArticleService articleService;


  @ApiOperation(value = "Register a new article", response = String.class)
  @ApiResponses(value = {
    @ApiResponse(code = 201, message = "Successfully registered"),
    @ApiResponse(code = 400, message = "Some required fields are invalid or missing"),
    @ApiResponse(code = 409, message = "If the article already exists")
  })
  @RequestMapping(path = {"", "/add"}, method = RequestMethod.POST)
  public ResponseEntity<?> create(@Valid @RequestBody ArticleDTO articleData, Principal authUser) {
    try {
      String username = authUser.getName();
      UserDTO currentUser = new UserDTO();
      currentUser.setUsername(username);
      articleService.create(articleData, currentUser);
    } catch (ArticleAlreadyExistException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    return new ResponseEntity<>(articleData, HttpStatus.CREATED);
  }
}

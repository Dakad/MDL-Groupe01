package be.unamur.info.mdl.ctrler;

import static be.unamur.info.mdl.ctrler.ApiControllerUtils.KEY_MESSSAGE;

import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.BookmarkDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.exceptions.AlreadyBookmarkedException;
import be.unamur.info.mdl.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/article")
@Api(value = "Operations related to an Article such as add, get, delete ...")
public class ArticleController {

  @Autowired
  private ArticleService articleService;


  @ApiOperation(value = "Register a new article")
  @ApiResponses(value = {
    @ApiResponse(code = 201, message = "Successfully registered"),
    @ApiResponse(code = 400, message = "Some required fields are invalid or missing"),
    @ApiResponse(code = 409, message = "If the article already exists", response = String.class)
  })
  @PostMapping(path = {"", "/"})
  public ResponseEntity create(@Valid @RequestBody ArticleDTO articleData, Principal authUser) {
    String username = authUser.getName();
    UserDTO currentUser = new UserDTO();
    currentUser.setUsername(username);
    articleService.create(articleData, currentUser);
    return new ResponseEntity<>(articleData, HttpStatus.CREATED);
  }


  @ApiOperation(value = "Retrieve a list of article by their references")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "List of the matching articles", response = ArticleDTO[].class),
    @ApiResponse(code = 400, message = "The list of provided references is empty"),
    @ApiResponse(code = 404, message = "The provided reference doesn't exist")
  })
  @GetMapping(name = "GET_articles_by_references", path = {"", "/"}, params = "reference")
  public List<ArticleDTO> listByReferences(
    @RequestParam(name = "reference") List<@NotBlank(message = "The reference must be defined") String> references) {
    return articleService.listArticleByReferences(references);
  }

  @ApiOperation(value = "Retrieve a specific article by it reference")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully registered", response = ArticleDTO.class),
    @ApiResponse(code = 404, message = "The provided reference doesn't exist")
  })
  @GetMapping(path = "/{reference}")
  public ResponseEntity get(@PathVariable String reference) {

    ArticleDTO articleData = articleService.getArticleByReference(reference);
    return new ResponseEntity(articleData, HttpStatus.OK);

  }


  @ApiOperation(value = "Retrieve a list of article by their categories")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "List of the matching articles"),
  })
  @GetMapping(name = "GET_articles_by_categories", path = "", params = "category")
  public Map<String, List<ArticleDTO>> listByCategories(
    @RequestParam(name = "category") List<String> categories) {
    return articleService.listArticleByCategories(categories);
  }


  @ApiOperation(value = "Create a new bookmark on an article")
  @ApiResponses(value = {
    @ApiResponse(code = 201, message = "Successfully created"),
    @ApiResponse(code = 400, message = "The referenced article is already bookmarked"),
    @ApiResponse(code = 404, message = "The provided reference doesn't exist")
  })
  @PostMapping(path = "/{reference}/bookmark")
  public ResponseEntity addBookmark(@PathVariable String reference, Principal authUser,
    @ApiParam(name = "note", value = "A note about the bookmark") @RequestBody(required = false) BookmarkDTO data)
    throws AlreadyBookmarkedException {

    articleService.addBookmark(reference, authUser.getName(), data.getNote());
    String responsesMsg = ApiControllerUtils.formatToJSON(KEY_MESSSAGE, "Bookmark added");
    return ResponseEntity.status(HttpStatus.CREATED).body(responsesMsg);
  }


  @ApiOperation(value = "Remove a bookmark on an article")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully removed"),
    @ApiResponse(code = 404, message = "The provided reference doesn't exist"),
    @ApiResponse(code = 404, message = "The specified article is not bookmarked")
  })
  @DeleteMapping(path = "/{reference}/bookmark")
  public ResponseEntity removeBookmark(
    @ApiParam(name = "reference", value = "The article reference")
    @PathVariable String reference,
    Principal authUser) {

    if (articleService.removeBookmark(reference, authUser.getName())) {
      String responsesMsg = ApiControllerUtils.formatToJSON(KEY_MESSSAGE, "Bookmark removed");
      return ResponseEntity.status(HttpStatus.OK).body(responsesMsg);
    } else {
      //TODO Add more explicit error message
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
    }
  }


  @GetMapping(path = "/{reference}/bookmarked")
  public ResponseEntity isBookmarked(@PathVariable String reference, Principal authUser) {
    String msg;
    if(articleService.isBookmarked(reference, authUser.getName())){
      msg = "This article is present your bookmarks";
    } else {
      msg = "This article is not present your bookmarks";
    }

    String responsesMsg = ApiControllerUtils.formatToJSON(KEY_MESSSAGE, msg);
    return ResponseEntity.status(HttpStatus.OK).body(responsesMsg);
  }

}

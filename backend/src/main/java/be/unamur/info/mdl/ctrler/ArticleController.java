package be.unamur.info.mdl.ctrler;

import static be.unamur.info.mdl.ctrler.ApiControllerUtils.KEY_MESSAGE;

import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.BookmarkDTO;
import be.unamur.info.mdl.dto.DefaultResponseDTO;
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
import javax.servlet.http.HttpServletRequest;
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

    boolean done = articleService.addBookmark(reference, authUser.getName(), data.getNote());
    String msg = "Bookmark " + (!done ? "not" : "") + " added";
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(DefaultResponseDTO.builder().done(done).message(msg));
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

    boolean done = articleService.removeBookmark(reference, authUser.getName());
    String msg = "Bookmark " + (!done ? "not" : "") + " removed";
    return ResponseEntity.status(HttpStatus.OK)
      .body(DefaultResponseDTO.builder().done(done).message(msg));

  }


  @GetMapping(path = "/{reference}/bookmarked")
  public ResponseEntity isBookmarked(@PathVariable String reference, Principal authUser) {
    boolean done = articleService.isBookmarked(reference, authUser.getName());
    String msg = "This article is " + (!done ? "not" : "") + " present your bookmarks";
    return ResponseEntity.status(HttpStatus.OK)
      .body(DefaultResponseDTO.builder().done(done).message(msg));
  }

  @GetMapping(path = "/subscriptions")
  public ResponseEntity subscriptions(@RequestParam(defaultValue = "1") int p, Principal authUser) {
    if(p<=0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Page number cannot be less than 1");
    return ResponseEntity.status(HttpStatus.OK).body(articleService.getSubscriptions(authUser.getName(),p));
  }

  @GetMapping(path = "/recommended")
  public ResponseEntity recommandations(@RequestParam(defaultValue = "1") int p, HttpServletRequest request){
    if(p<=0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Page number cannot be less than 1");
    String username = (request.getUserPrincipal() != null)? request.getUserPrincipal().getName() : null;
    return ResponseEntity.status(HttpStatus.OK).body(articleService.getRecommended(username,p));
  }

}

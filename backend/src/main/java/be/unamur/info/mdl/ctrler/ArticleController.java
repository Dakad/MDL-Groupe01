package be.unamur.info.mdl.ctrler;

import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.service.ArticleService;
import be.unamur.info.mdl.service.exceptions.ArticleAlreadyExistException;
import io.swagger.annotations.Api;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/article")
@Api(value = "Article_controller", description = "Operations of ArticleControler")
public class ArticleController extends APIBaseController {
  @Autowired
  private ArticleService articleService;


  @PostMapping(path = "/add")
  public ResponseEntity<?> create(@Valid @RequestBody ArticleDTO articleData) {
    try {
      articleService.create(articleData);
    } catch (ArticleAlreadyExistException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    return  new ResponseEntity<ArticleDTO>(articleData, HttpStatus.OK);

  }
}

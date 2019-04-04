package be.unamur.info.mdl.ctrler;

import be.unamur.info.mdl.dal.repository.ArticleRepository;
import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.service.ArticleService;
import io.swagger.annotations.Api;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/user/article/add")
@Api(value = "Article_controller", description = "Operations of ArticleControler")
public class ArticleController extends APIBaseController {
  private ArticleService articleService;
  private ArticleRepository repository;

  @PostMapping(path = "/article")
  public ResponseEntity<ArticleDTO> create(@Valid @RequestBody ArticleDTO articleData) {
   return  new ResponseEntity<ArticleDTO>(articleData, HttpStatus.OK);

  }
}

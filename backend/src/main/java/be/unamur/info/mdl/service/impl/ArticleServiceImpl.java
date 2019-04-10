package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.entity.ArticleEntity;
import be.unamur.info.mdl.dal.repository.ArticleRepository;
import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.service.ArticleService;
import be.unamur.info.mdl.service.exceptions.ArticleAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

  private ArticleRepository articleRepository;


  @Autowired
  public ArticleServiceImpl (ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  @Override
  public boolean create(ArticleDTO articleData) throws ArticleAlreadyExistException {

    if (articleRepository.existsByTitle(articleData.getTitle())) {
      throw new ArticleAlreadyExistException(articleData.getTitle() + " is already taken.");
    }

    this.articleRepository.save(ArticleEntity.of(articleData));
    return true;
  }


}

package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.entity.ArticleEntity;
import be.unamur.info.mdl.dal.repository.ArticleRepository;
import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.service.ArticleService;
import be.unamur.info.mdl.service.exceptions.ArticleException;
import org.springframework.stereotype.Service;

@Service("articleService")
public class ArticleServImpl implements ArticleService {
  private ArticleRepository articleRepository;

  @Override
  public boolean create(ArticleDTO articleData) throws ArticleException {
    if (articleData == null) {
      throw new ArticleException("Empty user data received.");
    }

    if (articleRepository.existsByTitle(articleData.getTitle())) {
      throw new ArticleException(articleData.getTitle() + " is already taken.");
    }

    this.articleRepository.save(ArticleEntity.of(articleData));
    return true;
  }


}

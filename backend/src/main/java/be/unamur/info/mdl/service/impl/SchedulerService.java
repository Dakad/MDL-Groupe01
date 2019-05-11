package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.entity.ArticleEntity;
import be.unamur.info.mdl.dal.repository.ArticleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service("schedulerService")
public class SchedulerService {

  private ArticleRepository articleRepository;

  @Autowired
  public SchedulerService(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  @Scheduled(fixedRate = 21600000, initialDelay = 1000)//6 hours = 21600000ms
  private void updateScores() {
    List<ArticleEntity> articles = articleRepository.findAll();
    articles.forEach(a -> {
      a.updateScore();
      articleRepository.save(a);
    });
  }
}

package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service("schedulerService")
public class SchedulerService {

  private ArticleService articleService;

  @Autowired
  public SchedulerService(ArticleService articleService) {
    this.articleService = articleService;
  }

  @Scheduled(fixedRate = 21600000, initialDelay = 1000)//6 hours = 21600000ms
  private void updateScores() {
    this.articleService.updateScores();
  }
}

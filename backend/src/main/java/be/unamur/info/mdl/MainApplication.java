package be.unamur.info.mdl;

import be.unamur.info.mdl.config.DBFillerRunner;
import be.unamur.info.mdl.service.ArticleService;
import be.unamur.info.mdl.service.UserService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder appBuilder) {
    return appBuilder.sources(MainApplication.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }


  @Bean
  public CommandLineRunner runDBFiller(UserService userService, ArticleService articleService) {
    return new DBFillerRunner(userService, articleService);
  }

}

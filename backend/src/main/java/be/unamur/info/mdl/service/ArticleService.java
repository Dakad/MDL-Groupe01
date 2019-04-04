package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.service.exceptions.ArticleException;

public interface ArticleService {

  public boolean create (ArticleDTO article) throws ArticleException;


}

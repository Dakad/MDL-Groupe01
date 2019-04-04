package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.service.exceptions.ArticleAlreadyExistException;

public interface ArticleService {

  boolean create (ArticleDTO article) throws ArticleAlreadyExistException;


}

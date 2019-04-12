package be.unamur.info.mdl.service;

import be.unamur.info.mdl.dto.ArticleDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.exceptions.ArticleAlreadyExistException;

public interface ArticleService {

  boolean create(ArticleDTO article, UserDTO creator) throws ArticleAlreadyExistException;


}
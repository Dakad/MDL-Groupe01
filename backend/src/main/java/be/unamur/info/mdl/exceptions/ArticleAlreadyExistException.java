package be.unamur.info.mdl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ArticleAlreadyExistException extends RuntimeException {

  public ArticleAlreadyExistException(String s) {
    super(s);
  }
}

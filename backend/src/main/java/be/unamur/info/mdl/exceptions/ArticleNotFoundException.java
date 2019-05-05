package be.unamur.info.mdl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ArticleNotFoundException extends RuntimeException {

  public static final String DEFAULT_MESSAGE = "The referenced article was not found";

  public ArticleNotFoundException() {
    this(DEFAULT_MESSAGE);
  }

  public ArticleNotFoundException(String s) {
    super(s);
  }
}

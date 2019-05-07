package be.unamur.info.mdl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookmarkNotFoundException extends RuntimeException {

  public static final String DEFAULT_MESSAGE = "The referenced bookmark was not found";

  public BookmarkNotFoundException() {
    this(DEFAULT_MESSAGE);
  }

  public BookmarkNotFoundException(String s) {
    super(s);
  }
}

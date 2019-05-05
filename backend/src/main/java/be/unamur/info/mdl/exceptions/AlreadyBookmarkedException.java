package be.unamur.info.mdl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyBookmarkedException extends RuntimeException {

  public static final String DEFAULT_MESSAGE = "It is already in your bookmarks";

  public AlreadyBookmarkedException() {
    super(DEFAULT_MESSAGE);
  }

  public AlreadyBookmarkedException(String s) {
    super(s);
  }
}

package be.unamur.info.mdl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AutoFollowedException extends RuntimeException {

  public static final String DEFAULT_MESSAGE = "If you followed yourself, you'd be walking in circles";

  public AutoFollowedException() {
    this(DEFAULT_MESSAGE);
  }

  public AutoFollowedException(String s) {
    super(s);
  }
}

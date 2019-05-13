package be.unamur.info.mdl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

  public static final String DEFAULT_MESSAGE = "Username does not exist";

  public UserNotFoundException() {
    this(DEFAULT_MESSAGE);
  }

  public UserNotFoundException(String s) {
    super(s);
  }
}

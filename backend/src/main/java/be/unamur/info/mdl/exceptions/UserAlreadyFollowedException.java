package be.unamur.info.mdl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyFollowedException extends RuntimeException {

  public static final String DEFAULT_MESSAGE = "User already followed";

  public UserAlreadyFollowedException() {
    this(DEFAULT_MESSAGE);
  }

  public UserAlreadyFollowedException(String s) {
    super(s);
  }
}

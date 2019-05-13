package be.unamur.info.mdl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SotaAlreadyExistException extends RuntimeException {

  public static final String DEFAULT_MESSAGE = "This SoTA is already saved";

  public SotaAlreadyExistException() {
    super(DEFAULT_MESSAGE);
  }

  public SotaAlreadyExistException(String s) {
    super(s);
  }
}

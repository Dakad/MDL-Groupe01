package be.unamur.info.mdl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SotaNotFoundException extends RuntimeException {

  public static final String DEFAULT_MESSAGE = "The referenced SoTA was not found";

  public SotaNotFoundException() {
    this(DEFAULT_MESSAGE);
  }

  public SotaNotFoundException(String e) {
    super(e);
  }

}

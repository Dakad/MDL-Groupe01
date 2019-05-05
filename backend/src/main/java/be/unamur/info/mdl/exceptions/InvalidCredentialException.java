package be.unamur.info.mdl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidCredentialException extends RuntimeException {

  public InvalidCredentialException(String e) {
    super(e);
  }
}

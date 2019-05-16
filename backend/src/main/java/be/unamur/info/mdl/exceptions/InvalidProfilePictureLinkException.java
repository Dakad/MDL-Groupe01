package be.unamur.info.mdl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidProfilePictureLinkException extends RuntimeException {

  public InvalidProfilePictureLinkException(String s) {
    super(s);
  }
}

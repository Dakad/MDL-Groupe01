package be.unamur.info.mdl.service.exceptions;

public class SotaAlreadyExistException extends Exception {

  public static final String DEFAULT_MESSAGE = "This SoTA is already saved";

  public SotaAlreadyExistException() {
    super(DEFAULT_MESSAGE);
  }

  public SotaAlreadyExistException(String s) {
    super(s);
  }
}

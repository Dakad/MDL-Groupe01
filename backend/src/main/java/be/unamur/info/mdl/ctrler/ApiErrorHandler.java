package be.unamur.info.mdl.ctrler;

import be.unamur.info.mdl.exceptions.ApiError;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
@Slf4j
public class ApiErrorHandler {

  // @Validate For Validating Path Variables and Request Parameters
  @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class,
    BindException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public Map<String, Object> handleValidationExceptions(Exception e, WebRequest request) {
    log.error("ValidationException ({}) : ", e.getClass().getName(), e.getCause());

    StringBuilder validationMsg = new StringBuilder();
    validationMsg.append("Some required fields are not valid");
    validationMsg.append(".\n.Please verify and try again");

    Object validationError = null;
    ApiError apiError = new ApiError();
    apiError.setStatus(HttpStatus.BAD_REQUEST);
    apiError.setMessage(validationMsg.toString());

    Map<String, Object> error = apiError.toMap(request);

    if (e instanceof ConstraintViolationException) {
      ConstraintViolationException ex = (ConstraintViolationException) e;
      //Get all fields errors
      validationError = ex.getConstraintViolations().stream()
        .map(ConstraintViolation::getMessage)
        .collect(Collectors.toList());
    }

    if (e instanceof BindException) {
      BindException ex = (BindException) e;
      // Get all fields and their matching error message
      validationError = ex.getBindingResult().getFieldErrors().stream()
        .collect(Collectors.toMap(FieldError::getField, f -> f.getDefaultMessage()));

    }

    if (e instanceof MethodArgumentNotValidException) {
      MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
      // Get all fields and their matching error message
      validationError = ex.getBindingResult().getFieldErrors().stream()
        .collect(Collectors.toMap(FieldError::getField, f -> f.getDefaultMessage()));

    }

    log.error("ValidationException (response error) : {} ", error);

    error.put("error", validationError);

    return error;
  }

  // JwtToken Exception

  @ExceptionHandler({
    SignatureException.class,
    MalformedJwtException.class,
    ExpiredJwtException.class,
    UnsupportedJwtException.class,
  })
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ResponseBody
  public Map<String, Object> handleJwtException(final Exception ex, final WebRequest request) {

    log.info(ex.getClass().getName(), ex);

    final String message = new StringBuilder()
      .append("Invalid Authorization token provided")
      .append("\nPlease login again to receive a new token")
      .toString();

    ApiError apiError = new ApiError();
    apiError.setStatus(HttpStatus.UNAUTHORIZED);
    apiError.setError("INVALID_TOKEN");
    apiError.setMessage(message);
    return apiError.toMap(request);
  }

  // 405 - METHOD_NOT_ALLOWED

  @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @ResponseBody
  public Map<String, Object> handleHttMethodNotSupported(
    final HttpRequestMethodNotSupportedException ex, final WebRequest request) {
    log.info(ex.getClass().getName());

    final StringBuilder message = new StringBuilder();
    message.append(ex.getMethod())
      .append(" method is not supported for this request.")
      .append("\nOnly supported methods are");
    ex.getSupportedHttpMethods().forEach(m -> message.append(" ").append(m));

    ApiError apiError = new ApiError();
    apiError.setStatus(HttpStatus.METHOD_NOT_ALLOWED);
    apiError.setMessage(ex.getLocalizedMessage());
    apiError.setErrors(Arrays.asList(message.toString()));
    return apiError.toMap(request);

  }

  // 415 - UNSUPPORTED_MEDIA_TYPE

  @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
  @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
  @ResponseBody
  public Map<String, Object> handleHttpMediaTypeNotSupported(
    final HttpMediaTypeNotSupportedException ex, final WebRequest request) {
    log.info(ex.getClass().getName());

    final StringBuilder message = new StringBuilder();
    message.append(ex.getContentType())
      .append(" media type is not supported.")
      .append("\nOnly media types are");
    ex.getSupportedMediaTypes().forEach(m -> message.append(" ").append(m));

    ApiError apiError = new ApiError();
    apiError.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    apiError.setMessage(ex.getLocalizedMessage());
    apiError.setErrors(Arrays.asList(message.toString()));
    return apiError.toMap(request);
  }

  // 500 - INTERNAL_SERVER_ERROR

  @ExceptionHandler({
    IOException.class,
    ServletException.class,
    IllegalArgumentException.class,
    NullPointerException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public Map<String, Object> handleHttpMediaTypeNotSupported(
    final Exception ex, final WebRequest request) {

    log.info(ex.getClass().getName(), ex);

    final String message = new StringBuilder()
      .append("Something, somewhere, has gone sideways.")
      .append("\nSo basically, shit happens...")
      .toString();

    ApiError apiError = new ApiError();
    apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    apiError.setMessage(message);
    return apiError.toMap(request);
  }

}

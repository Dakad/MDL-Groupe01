package be.unamur.info.mdl.exceptions;

import java.util.Collection;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;


/**
 * This class represent the Model or error sent to the Api consumer.<br\> This class is meant to be
 * converted to Json.
 */
@Data
@AllArgsConstructor
@Component
public class ApiError extends DefaultErrorAttributes {

  @Builder.Default
  private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

  @Getter(AccessLevel.NONE)
  private String error;

  private Object message;

  private Collection errors;


  @Builder
  public ApiError() {
    super(false);
  }

  public ApiError(HttpStatus status, String message, Collection errors) {
    this();
    this.status = status;
    this.message = message;
    this.errors = errors;
  }

  /**
   * Retrieve the error type.
   *
   * @return the defined error type otherwise the Status name.
   */
  public String getError() {
    if (this.error == null && this.status != null) {
      return this.status.name();
    }
    return error;
  }

  /**
   * Retrieve the error message.
   *
   * @return the defined error message otherwise the Status reason phrase.
   */
  public String getMessage() {
    if (this.message == null && this.status != null) {
      return this.status.getReasonPhrase();
    }
    return (String) message;
  }


  public Map<String, Object> toMap(WebRequest webRequest) {
    Map<String, Object> data = this.getErrorAttributes(webRequest);
    if (this.getStatus() != null) {
      data.put("status", status.value());
    }
    if (this.getError() != null) {
      data.put("error", this.getError());
    }
    data.put("message", this.getMessage());
    data.put("errors", this.getErrors());

    return data;
  }


  public Map<String, Object> getErrorAttributes(WebRequest request) {
    return super.getErrorAttributes(request, false);
  }


  @Override
  public Map<String, Object> getErrorAttributes(WebRequest request, boolean withStackTrace) {
    return super.getErrorAttributes(request, false);
  }
}

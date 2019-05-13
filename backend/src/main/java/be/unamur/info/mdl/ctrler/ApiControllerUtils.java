package be.unamur.info.mdl.ctrler;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;



@Slf4j
@ControllerAdvice
public class ApiControllerUtils {

  public static final String KEY_MESSAGE = "message";

  public ApiControllerUtils() {
    super();
  }

  public static String formatToJSON(String key, Object data) {
    Map<String, Object> response = new HashMap<>();
    response.put(key, data);
    return response.toString();
  }

}

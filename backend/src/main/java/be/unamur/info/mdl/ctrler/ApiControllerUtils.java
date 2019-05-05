package be.unamur.info.mdl.ctrler;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;


@ControllerAdvice
@Slf4j
public class ApiControllerUtils {

  public static final String KEY_MESSSAGE = "message";

  public ApiControllerUtils() {
    super();
  }

  public static String formatToJSON(String key, Object data) {
    JSONObject response = new JSONObject();
    response.put(key, data);
    return response.toString();
  }

}

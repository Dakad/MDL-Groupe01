package be.unamur.info.mdl.ctrler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class APIBaseController {
/*

    protected String sendResponse(String key, Object data){
        JSONObject response = new JSONObject();
        response.put(key, data);
        return response.toString();
    }
*/


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    String handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> error = e.getBindingResult().getFieldErrors().stream()
            .collect(Collectors.toMap( f -> f.getField(), f -> f.getDefaultMessage()));
        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(error);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
      return String.format("{\"validation\" : %s }", json);
    }



}

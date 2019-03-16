package be.unamur.info.mdl.ctrler;


import be.unamur.info.mdl.dto.CredentialDTO;
import be.unamur.info.mdl.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

// import org.springframework.stereotype.Controller;


@RestController
@RequestMapping(path = "/api")
public class MainController {

  @Autowired
  private UserService userService;


  @GetMapping(path = "/register")
  public String register(@RequestBody String name, @RequestParam String email) {
    //
    return null;
  }


  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseStatus(code = HttpStatus.OK)
  @Validated
  public String login(@Valid @RequestBody CredentialDTO userDTO) {
    String result;
    if (userService.login(userDTO)) {
      result = String.format("{LOGIN SUCCESS: %b}", true);
    } else {
      result = String.format("{LOGIN SUCCESS: %b}", false);
    }
    return result;
  }

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
    return String.format("{\"params\" : %s }", json.toString());
  }

}

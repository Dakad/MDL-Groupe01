package be.unamur.info.mdl.ctrler;


import be.unamur.info.mdl.dto.CredentialDTO;
import be.unamur.info.mdl.dto.UserDTO;
import be.unamur.info.mdl.service.UserService;
import be.unamur.info.mdl.service.exceptions.InvalidCredentialException;
import be.unamur.info.mdl.service.exceptions.RegistrationException;
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
import org.springframework.http.ResponseEntity;
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


  @RequestMapping(path = "/register", method = RequestMethod.POST)
  public ResponseEntity<Map<String,String>> register(@Valid @RequestBody UserDTO userData) {
    Map<String,String> response = new HashMap<>();
    try {
      this.userService.register(userData);
      response.put("success", "New user registred");
      return new ResponseEntity(response, HttpStatus.CREATED);
    } catch (RegistrationException ex) {
      response.put("error", ex.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

  }


  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @Validated
  public ResponseEntity<Map<String,String>> login(@Valid @RequestBody CredentialDTO userDTO) {
    Map<String,String> result = new HashMap<>();
    try {
      String token = userService.login(userDTO);
      result.put("auth_token", token);
      return ResponseEntity.status(HttpStatus.OK).body(result);
    } catch (InvalidCredentialException ex) {
      result.put("auth_token", ex.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }
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
    return String.format("{\"validation\" : %s }", json.toString());
  }

}

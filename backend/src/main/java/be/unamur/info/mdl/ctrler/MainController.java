package be.unamur.info.mdl.ctrler;


import be.unamur.info.mdl.dto.CredentialDTO;
import be.unamur.info.mdl.dto.UserDTO;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import be.unamur.info.mdl.dal.entity.User;
import be.unamur.info.mdl.service.UserService;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(path = "/api")
public class MainController {

  @Autowired
  private UserService userService;


  @GetMapping(path = "/register")
  public  String register(@RequestBody String name, @RequestParam String email) {
    //
    return null;
  }


  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseStatus(code = HttpStatus.OK)
  public String login(@Valid @RequestBody CredentialDTO userDTO) {
    String result;
    if (userService.login(userDTO)) {
      result = String.format("{LOGIN SUCCESS: %b}", true);
    } else {
      result = String.format("{LOGIN SUCCESS: %b}", false);
    }
    return result;
  }

}

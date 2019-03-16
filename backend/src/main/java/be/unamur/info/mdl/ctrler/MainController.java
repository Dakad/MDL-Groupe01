package be.unamur.info.mdl.ctrler;


import be.unamur.info.mdl.dto.CredentialDTO;
import be.unamur.info.mdl.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
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
  public @ResponseBody String register(@RequestBody String name, @RequestParam String email) {
    //
    return null;
  }


	@RequestMapping(value="/login", method = RequestMethod.POST)
	public @ResponseBody String login(@RequestBody CredentialDTO userDTO){
		String result;
		if(userService.login(userDTO))	result = String.format("{LOGIN SUCCESS: %b}",true);
		else result = String.format("{LOGIN SUCCESS: %b}",false);
		return result;
	}

}

package be.unamur.info.mdl.ctrler;

import be.unamur.info.mdl.dto.PasswordChangeDTO;
import be.unamur.info.mdl.dto.ProfileBasicInfoDTO;
import be.unamur.info.mdl.dto.ProfileProInfoDTO;
import be.unamur.info.mdl.service.ProfileService;
import be.unamur.info.mdl.service.UserService;
import be.unamur.info.mdl.service.exceptions.UsernameNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping(path = "/api/user")
@Api(value = "user_controller", description = "Operations of UserControler")
public class UserController extends APIBaseController {

  @Autowired
  private UserService userService;
  @Autowired
  private ProfileService profileService;

  @ApiOperation(value = "Change the user password", response = String.class)
  @RequestMapping(path = "/{username}/changepwd", method = RequestMethod.POST)
  public @ResponseBody String changePassword(@PathVariable("username") String username,
    @RequestBody PasswordChangeDTO passwordChangeDTO) {
    if (userService.changePassword(username, passwordChangeDTO)) {
      return "OK";
    }
    return "ERROR : 409 CONFLICT";
  }

  @RequestMapping(path="/{username}/profile/base", method = RequestMethod.GET)
  public ResponseEntity getBasicInfo(@PathVariable String username){
    try{
      ProfileBasicInfoDTO dto = profileService.getBasicInfo(username);
      return ResponseEntity.status(HttpStatus.OK).body(dto);
    }catch(UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username not found");
    }

  }

  @RequestMapping(path="/{username}/profile/pro", method = RequestMethod.GET)
  public ResponseEntity getProInfo(@PathVariable String username){
    try{
      ProfileProInfoDTO dto = profileService.getProInfo(username);
      return ResponseEntity.status(HttpStatus.OK).body(dto);
    }catch(UsernameNotFoundException e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username not found");
    }
  }

}


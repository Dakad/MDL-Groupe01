package be.unamur.info.mdl.ctrler;

import be.unamur.info.mdl.dto.PasswordChangeDTO;
import be.unamur.info.mdl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(path="/{username}/changepwd")
    public @ResponseBody String changePassword(@PathVariable("username") String username, @RequestBody PasswordChangeDTO passwordChangeDTO){
        if(userService.changePassword(username,passwordChangeDTO)){
            return "OK";
        }
        return "ERROR : 409 CONFLICT";
    }

}


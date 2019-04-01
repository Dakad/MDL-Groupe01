package be.unamur.info.mdl.ctrler;

import be.unamur.info.mdl.dto.PasswordChangeDTO;
import be.unamur.info.mdl.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/user")
@Api(value="user_controller", description="Operations of user_controler")
public class UserController extends APIBaseController {
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


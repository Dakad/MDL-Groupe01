package be.unamur.info.mdl.ctrler;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/sota")
@Api(value = "sota_controller", description = "Operations of UserControler")
public class StateOfTheArtController {


}

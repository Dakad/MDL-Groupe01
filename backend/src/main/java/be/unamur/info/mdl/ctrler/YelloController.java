package be.unamur.info.mdl.ctrler;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class YelloController {

    @RequestMapping("/")
    public String index() {
        return "Yello Greetings from Spring Boot!";
    }

}
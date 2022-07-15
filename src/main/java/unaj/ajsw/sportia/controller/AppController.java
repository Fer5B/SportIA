package unaj.ajsw.sportia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping({"/", "/home"})
    public String index() {
        return "index";
    }

}

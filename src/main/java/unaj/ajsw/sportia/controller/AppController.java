package unaj.ajsw.sportia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import unaj.ajsw.sportia.dto.DataMail;
import unaj.ajsw.sportia.service.EmailSenderService;

@Controller
public class AppController {

    @Autowired
    private EmailSenderService emailSenderService;

    @GetMapping({"/", "/home"})
    public String index() {
        return "index";
    }

}

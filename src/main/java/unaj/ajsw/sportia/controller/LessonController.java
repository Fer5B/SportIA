package unaj.ajsw.sportia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import unaj.ajsw.sportia.service.LessonService;

import javax.annotation.Resource;

@Controller
public class LessonController {

    @Resource(name = "lessonService")
    private LessonService lessonService;

    @GetMapping("/clases")
    public String lessons(Model model){
        model.addAttribute("lessons", lessonService.findAll());
        return "lessons";
    }
}

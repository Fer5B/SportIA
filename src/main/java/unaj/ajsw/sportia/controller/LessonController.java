package unaj.ajsw.sportia.controller;

import com.google.inject.internal.util.Sets;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.PreferenceItem;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import unaj.ajsw.sportia.model.Inscription;
import unaj.ajsw.sportia.model.Lesson;
import unaj.ajsw.sportia.model.Location;
import unaj.ajsw.sportia.model.User;
import unaj.ajsw.sportia.service.InsctiptionService;
import unaj.ajsw.sportia.service.LessonService;
import unaj.ajsw.sportia.service.UserService;

import javax.annotation.Resource;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LessonController {

    @Resource(name = "lessonService")
    private LessonService lessonService;

    @Autowired
    private UserService usService;

    @Autowired
    private InsctiptionService inService;


    @PostMapping("/classes/create-class")
    public String newLesson(){ //@RequestBody Lesson lesson

        if(lessonService.findAll().isEmpty())
            lessonService.createLessons();

        return "redirect:/classes/recreational";
    }

    @GetMapping("/classes/{type}")
    public String lessons(Model model, @PathVariable String type,
                                @RequestParam(value = "range-time", required = false) String range_time, @RequestParam(required = false) String zone){

        List<Lesson> lessons = lessonService.filter(type, range_time, zone);
        Set<String> cities = new HashSet<>(lessonService.findLessonsByType(type).stream().map(lesson -> lesson.getLocation().getCity()).collect(Collectors.toList()));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usService.findUserByEmail(auth.getName());
        if(user != null) {
            List<Inscription> userInscriptions = inService.findInscriptionByUserId(user.getId());
            if(userInscriptions != null){
                List<ObjectId> enrolledLessonsId = userInscriptions.stream().map(Inscription::getLessonId).collect(Collectors.toList());
                model.addAttribute("enrolledLessonsId", enrolledLessonsId);
            }
        }
        model.addAttribute("currentUser", user);
        model.addAttribute("lessons", lessons);
        model.addAttribute("type", type);
        model.addAttribute("fullTimeRange", lessonService.getTimeRange(8,20));
        model.addAttribute("cities", cities);

        if(range_time != null) {
            String[] rangeTimeSplit = range_time.split("-");
            model.addAttribute("startTime", rangeTimeSplit[0]);
            model.addAttribute("endTime", rangeTimeSplit[1]);
        }

        if(zone != null)
            model.addAttribute("zone", zone);

        return "lessons";
    }

//    @GetMapping("/classes/inscription/{id}")
//    public String enrollForLessons(Model model, @PathVariable("id") String idLesson) {
//        Lesson lesson = lessonService.findLessonById(idLesson);
//        model.addAttribute("lesson", lesson);
//        return "inscription-lessons";
//    }

    @GetMapping("/my-inscriptions")
    public String getInscripciones(Model model, @RequestParam Set<ObjectId> lessonsId){
        List<Lesson> lessons = lessonService.findLessonsByListId(lessonsId);
        model.addAttribute("lessons", lessons);
        return "lessons";
    }

}

package unaj.ajsw.sportia.controller;

import com.google.gson.Gson;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import unaj.ajsw.sportia.dto.DataMailInscription;
import unaj.ajsw.sportia.dto.MpPayment;
import unaj.ajsw.sportia.dto.PreferenceDTO;
import unaj.ajsw.sportia.model.Inscription;
import unaj.ajsw.sportia.model.Lesson;
import unaj.ajsw.sportia.service.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Properties;

@Controller
public class MPSandboxController {

    private final Properties messages = new Properties();
    private PreferenceDTO preferenceDTO;

    @Resource(name = "mpService")
    private MPService mpService;

    @Autowired
    private LessonService ls;

    @Autowired
    private UserService us;

    @Autowired
    private InsctiptionService insctiptionService;


//    @PostMapping(value = "/classes/create-preference", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity createPreference(@RequestBody PreferenceDTO preferenceDTO) throws MPException, MPApiException {
//        return mpService.createPreference(preferenceDTO);
//    }

    @PostMapping(value = "/classes/create-preference", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createPreference(@RequestBody String preferenceDTO) throws MPException, MPApiException {
        this.preferenceDTO = new Gson().fromJson(preferenceDTO, PreferenceDTO.class);
        this.preferenceDTO.setPayer(us.findUserByEmail(this.preferenceDTO.getPayer().getEmail()));
        return mpService.createPreference(this.preferenceDTO);
    }

    @GetMapping("/classes/{type}/successful-payment")
    public String successfulPayment(@PathVariable String type, @RequestParam String status, @RequestParam String external_reference,
                                    @RequestParam String payment_type, @RequestParam String merchant_order_id,
                                    @RequestParam String preference_id) {
//        recibo MpPayment parametros y preference_id -> https://api.mercadopago.com/checkout/preferences/{id}

        insctiptionService.saveInscription(
                Inscription.builder()
                        .lessonId(new ObjectId(preferenceDTO.getLessonId()))
                        .userId(preferenceDTO.getPayer().getId())
                        .payment(MpPayment.builder()
                                .status(status)
                                .external_reference(external_reference)
                                .payment_type(payment_type)
                                .merchant_order_id(merchant_order_id)
                                .preference_id(preference_id)
                                .build())
                        .timestamp(LocalDateTime.now())
                        .build()
        );

        Lesson lesson = ls.findLessonById(preferenceDTO.getLessonId());
        insctiptionService.sendEmail(
                DataMailInscription.builder()
                        .senderEmail(preferenceDTO.getPayer().getEmail())
                        .title(messages.getProperty("email.inscription.title"))
                        .lesson(lesson)
                        .build()
        );

        if(lesson.getEnrolledId() == null)
            lesson.setEnrolledId(new HashSet<>());

        lesson.getEnrolledId().add(preferenceDTO.getPayer().getId());
        ls.saveLesson(lesson);

        return "redirect:/classes/"+type;
    }

    @GetMapping("/classes/{type}/pending-payment")
    public String pendingPayment(Model model, @PathVariable String type, @RequestParam String status, @RequestParam String external_reference,
                                 @RequestParam String payment_type, @RequestParam String merchant_order_id,
                                 @RequestParam String preference_id) {

        insctiptionService.saveInscription(
                Inscription.builder()
                        .lessonId(new ObjectId(preferenceDTO.getLessonId()))
                        .userId(preferenceDTO.getPayer().getId())
                        .payment(MpPayment.builder()
                                .status(status)
                                .external_reference(external_reference)
                                .payment_type(payment_type)
                                .merchant_order_id(merchant_order_id)
                                .preference_id(preference_id)
                                .build())
                        .timestamp(LocalDateTime.now())
                        .build()
        );

        Lesson lesson = ls.findLessonById(preferenceDTO.getLessonId());
        insctiptionService.sendEmail(
                DataMailInscription.builder()
                        .senderEmail(preferenceDTO.getPayer().getEmail())
                        .title(messages.getProperty("email.inscription.title"))
                        .lesson(lesson)
                        .build()
        );

        return "redirect:/classes/"+type;
    }

    @GetMapping("/classes/{type}/failed-payment")
    public String failedPayment(@PathVariable String type){
        return "redirect:/classes/"+type;
    }

}

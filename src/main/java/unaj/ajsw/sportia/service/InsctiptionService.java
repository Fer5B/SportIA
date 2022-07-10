package unaj.ajsw.sportia.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import unaj.ajsw.sportia.dto.DataMailInscription;
import unaj.ajsw.sportia.model.Inscription;
import unaj.ajsw.sportia.repository.InscriptionRepository;

import java.util.*;

@Service("insctiptionService")
public class InsctiptionService {

    private InscriptionRepository inscriptionRepository;
    private EmailSenderService emailSenderService;

    @Autowired
    private TemplateEngine templateEngine;

    public InsctiptionService (InscriptionRepository inscriptionRepository, EmailSenderService emailSenderService){
        this.inscriptionRepository = inscriptionRepository;
        this.emailSenderService = emailSenderService;
    }

    public void saveInscription(Inscription inscription) {
        inscriptionRepository.save(inscription);
    }

    public void sendEmail(DataMailInscription dataMailInscription) {
        dataMailInscription.setBodyMsg(createInscriptionTemplate(dataMailInscription));
        emailSenderService.sendEmail(dataMailInscription, Collections.EMPTY_LIST);
    }

    private String createInscriptionTemplate(DataMailInscription dataMailInscription) {
        String output = "";

        try{
            Map<String, Object> values = new HashMap<>();
            values.put("data", dataMailInscription);

            final String templateFileName = "inscription-mail";
            output = this.templateEngine.process(templateFileName, new Context(LocaleContextHolder.getLocale(), values));
        }
        catch (Exception e){}

        return output;
    }

    public List<Inscription> findInscriptionByUserId(ObjectId userId) {
        return inscriptionRepository.findInscriptionByUserId(userId);
    }

}

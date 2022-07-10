package unaj.ajsw.sportia.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import unaj.ajsw.sportia.dto.DataMail;
import unaj.ajsw.sportia.dto.DataMailInscription;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Service("emailSenderService")
public class EmailSenderService {
    private final Properties properties = new Properties();
    private final TemplateEngine templateEngine;

    private String password;
    private Session session;

    public EmailSenderService(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    private void init() {

        properties.put("mail.smtp.host", "ca8.toservers.com");
        properties.put("mail.smtp.starttls.enable", "false");
        properties.put("mail.smtp.port", 26);
        properties.put("mail.smtp.sender", "web@c3l.com.ar");
        properties.put("mail.smtp.user", "web@c3l.com.ar");
        properties.put("mail.smtp.auth", "true");

        session = Session.getDefaultInstance(properties);
    }

    public ResponseEntity sendEmail(DataMail dataMail, List<File> attachedFiles){

        init();
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress((String)properties.get("mail.smtp.sender")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(dataMail.getSenderEmail()));
//            message.addRecipient(Message.RecipientType.BCC, new InternetAddress("bcc@mail.com"));
            message.setSubject(dataMail.getTitle());

//            make body message
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(dataMail.getBodyMsg(), "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            addAttachedFiles(attachedFiles, multipart);
            message.setContent(multipart);

            Transport t = session.getTransport("smtp");
            t.connect((String)properties.get("mail.smtp.user"), "Zaxy3019#$.91ku");
            message.saveChanges();
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        }catch (MessagingException me){
            // Mostrar un mensaje de error o lanzarla para que el módulo superior la capture
            return ResponseEntity.badRequest().body("Error when trying to send mail.\n"+me.getMessage());
        }

        return ResponseEntity.ok("Email send!");
    }

    private void addAttachedFiles(List<File> attachedFiles, Multipart multipart) {
        if (attachedFiles == null)
            return;

        attachedFiles.forEach( file -> {
            MimeBodyPart mbp = new MimeBodyPart();
            try {
                mbp.attachFile(file);
                multipart.addBodyPart(mbp);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
    }

}

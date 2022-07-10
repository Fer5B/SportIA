package unaj.ajsw.sportia.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import unaj.ajsw.sportia.model.Lesson;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DataMail {
    private String senderEmail;
    private String title;
    private String bodyMsg;
}


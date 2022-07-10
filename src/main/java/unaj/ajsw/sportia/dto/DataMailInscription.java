package unaj.ajsw.sportia.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import unaj.ajsw.sportia.model.Lesson;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DataMailInscription extends DataMail {
    private Lesson lesson;
}

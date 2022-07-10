package unaj.ajsw.sportia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import unaj.ajsw.sportia.dto.MpPayment;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="inscriptions")
public class Inscription {
    @MongoId
    private ObjectId id;
    private ObjectId lessonId;
    private ObjectId userId;
    private MpPayment payment;
    private LocalDateTime timestamp;
}

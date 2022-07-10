package unaj.ajsw.sportia.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="lessons")
public class Lesson {
    @MongoId
    private ObjectId id;
    private String name;
    @Indexed(unique = true)
    private LessonType type;
//    private LocalDateTime dateTime;
    private int weekDayNumber;
//    private LocalTime startTime;
    private String startTime;
    private int durationInMinutes;
    private Location location;
    private int capacity;
    private double price;
    private LessonModality modality;
    private String purpose;
    private Boolean active;
    private Set<ObjectId> enrolledId;
}


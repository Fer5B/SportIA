package unaj.ajsw.sportia.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@Setter
@Document(collection="lessons")
public class Lesson {
    @MongoId
    private ObjectId id;
    private String name;
    private LessonType type;
    private List<String> days;
    private String startTime;
    private String endTime;
    private String place;
    private int capacity;
    private float price;
    private LessonModality modality;
    private String purpose;
    private Boolean estado;

    public Lesson(String name, LessonType type, LessonModality modality) {
        this.name = name;
        this.type = type;
        this.modality = modality;
    }

    public Lesson(String name, LessonType type, LessonModality modality, List<String> days, String startTime, String endTime) {
        this(name, type, modality);
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Lesson(String name, LessonType type, LessonModality modality, List<String> days, String startTime, String endTime, String place, int capacity, float price) {
        this(name, type, modality, days, startTime, endTime);
        this.place = place;
        this.capacity = capacity;
        this.price = price;
    }

}


package unaj.ajsw.sportia.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "locations")
public class Location {
    @MongoId
    private ObjectId id;
    private String country;
    private String state;
    private String city;
    private String zipCode;
    private String street;
    private int number;
    private int floor;
    private String apartment;
}

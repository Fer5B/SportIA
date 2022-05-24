package unaj.ajsw.sportia.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Document(collection = "locations")
public class Location {
    @MongoId
    private ObjectId id;
    private String country;
    private String province;
    private String city;
    private String code;
    private String street;
    private int number;
    private int piso;
    private String departamento;

    public Location(String country, String province, String city, String code, String street, int number) {
        this.country = country;
        this.province = province;
        this.city = city;
        this.code = code;
        this.street = street;
        this.number = number;
    }
}

package unaj.ajsw.sportia.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Document(collection = "users")
public class User {
    @MongoId
    private ObjectId id;
    private String name;
    private String lastName;
    private int age;
    private int dni;
    private String email;
    private String phone;
    private UserRole role;
    private Location location;

    public User (){}
    public User(String name, String lastName, int age, int dni, String email, String phone, UserRole role){
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.dni = dni;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public String getFullName() {
        return name + " " + lastName;
    }
}


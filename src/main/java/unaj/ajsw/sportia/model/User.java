package unaj.ajsw.sportia.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @MongoId
    private ObjectId id;
    private String name;
    private String lastName;
    private int age;
    private int dni;
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    private String email;
    private String password;
    private String phone;
//    @DBRef
    private Set<UserRole> roles;
    private Location location;
    private boolean enabled;
//    private Set<ObjectId> inscriptionsId;

    public String getFullName() {
        return name + " " + lastName;
    }
}


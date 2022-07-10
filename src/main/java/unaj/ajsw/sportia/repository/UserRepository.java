package unaj.ajsw.sportia.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import unaj.ajsw.sportia.model.User;

import java.util.List;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    @Query("{ 'email' : ?0 }")
    User findByEmail(String email);
    @Query("{ 'name' : ?0 }")
    List<User> findUsersByName(String name);
}

package unaj.ajsw.sportia.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import unaj.ajsw.sportia.model.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {}

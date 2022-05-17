package unaj.ajsw.sportia.service;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import unaj.ajsw.sportia.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findUserById(ObjectId id);
    void deleteUserById(ObjectId id);
}

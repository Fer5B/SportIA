package unaj.ajsw.sportia.service;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import unaj.ajsw.sportia.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findUserById(ObjectId id);
    User saveUser(User newUser);
    void deleteUserById(ObjectId id);

    User validateLogin(String username, String password);

//    test
    User createUser(String name, String lastName, int age);
    User createUserAdmin(String name, String lastName, int age);
}

package unaj.ajsw.sportia.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unaj.ajsw.sportia.model.User;
import unaj.ajsw.sportia.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service("userService")
public class DefaultUserService implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserById(ObjectId id) {
        Optional<User> userData = userRepository.findById(id);

        if(userData.isPresent())
            return userData.get();

        return null;
    }

    @Override
    public void deleteUserById(ObjectId id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }
}

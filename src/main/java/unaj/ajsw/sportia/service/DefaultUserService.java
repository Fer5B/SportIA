package unaj.ajsw.sportia.service;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import unaj.ajsw.sportia.model.Location;
import unaj.ajsw.sportia.model.User;
import unaj.ajsw.sportia.model.UserRole;
import unaj.ajsw.sportia.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service("userService")
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
    public User validateLogin(String username, String password) {
        return null;
    }

    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user){
        return userRepository.save(user);
    }

    //    ----- Métodos para test -----
    public Location getDefaultLocation(){
        return new Location("Argentina", "Buenos Aires", "Florencio Varela",
                "C8291", "Calchaquí", 6720);
    }
    @Override
    public User createUser(String name, String lastName, int age){
        User user = new User(name, lastName, age, 10000000, "default@email.com", "0", UserRole.ROLE_USER);
        user.setLocation(getDefaultLocation());
        return user;
    }
    @Override
    public User createUserAdmin(String name, String lastName, int age){
        User user = new User(name, lastName, age, 10000000, "default@email.com", "0", UserRole.ROLE_ADMIN);
        user.setLocation(getDefaultLocation());
        return user;
    }
//    -----------------------------
}

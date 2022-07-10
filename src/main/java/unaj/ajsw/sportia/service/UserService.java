package unaj.ajsw.sportia.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import unaj.ajsw.sportia.dto.DataUser;
import unaj.ajsw.sportia.model.Location;
import unaj.ajsw.sportia.model.User;
import unaj.ajsw.sportia.model.UserRole;
import unaj.ajsw.sportia.repository.UserRepository;
import unaj.ajsw.sportia.validation.EmailValidator;
import unaj.ajsw.sportia.validation.PasswordConstraintValidator;

import java.util.*;

@Service("userService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private PasswordConstraintValidator passwordValidator;



    /**
     * Método encargado de buscar a un usuario del sistema por su id.
     * @param id
     * @return unaj.ajsw.sportia.model.User
     * */
    public User findUserById(ObjectId id) {
        Optional<User> userData = userRepository.findById(id);
        return userData.orElse(null);
    }

    /**
     * Este método elimina al usuario del sistema que coincida con el Id ingresado por parámetro.
     * @param id ObjectId
     * */
    public void deleteUserById(ObjectId id) {
        userRepository.deleteById(id);
    }

    /**
     * Método encargado de buscar un usuario de la base de datos por medio del email ingresado por parámetro.
     * @param email Email del usuario
     * @return {@link User}
     * */
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Método encargado de determinar sin un email pertenece a un usuario almacenado en el sistema.
     * @param email
     * @return boolean
     * */
    public boolean isEmailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    /**
     * Este método se encarga de buscar una lista de usuarios de la base de datos
     * que coincidan con el nombre ingresado por parámetro.
     * @param name Nombre del usuario.
     * @return List<unaj.ajsw.sportia.User>
     * */
    public List<User> findUsersByName(String name){
        return userRepository.findUsersByName(name);
    }

    /**
     * Implementación del método de la interfaz {@link UserDetailsService},
     * encargado de verificar y validar una correcta autenticación del usuario y
     * determinar el nivel de autorización que este posee en el sistema.
     * <p>
     * @param email
     * @return UserDetails
     * @exception UsernameNotFoundException
     * */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if(user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }

    public User registerNewUserAccount(DataUser dataUser, BindingResult bindingResult) {

//        dataUser validation
        checkEmptyFields(dataUser, bindingResult);
        validateEmail(dataUser, bindingResult);
        validatePassword(dataUser, bindingResult);

        if(! bindingResult.hasErrors()) {
            return saveUser(User.builder()
                        .name(dataUser.getName())
                        .lastName(dataUser.getLastName())
                        .email(dataUser.getEmail())
                        .password(dataUser.getPassword())
                    .build());
        }

        return null;
    }

    /**
     * Método encargado de retornar una lista completa con todos los usuarios de la base de datos.
     * @return List. Lista de unaj.ajsw.sportia.{@link User}
     * */
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    /**
     * Método encargado de almacenar un nuevo usuario a la base de datos del sistema.
     * <p> 1. Se aplica un hash a la password
     * <p> 2. Se setea los roles que por defecto tendrá todo nuevo usuario
     * <p> 3. Se lo habilita para utilizar el sistema
     * <p> 4. Mediante la interfaz {@link UserRepository} se lo almacena en la base de datos
     * @param user
     * @return unaj.ajsw.sportia.User>
     * */
    public User saveUser(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(Collections.singletonList(UserRole.ROLE_USER)));
        user.setEnabled(true);
        return userRepository.save(user);
    }

    /**
     * A partir de la lista Set de Roles del usuario se obtiene la
     * lista de {@link GrantedAuthority} necesaria para realizar
     * el proceso de autenticación del usuario.
     * @param userRoles Set<UserRole>
     * @return List<GrantedAuthority>
     * */
    private List<GrantedAuthority> getUserAuthority(Set<UserRole> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> roles.add(new SimpleGrantedAuthority(role.name())));
        return new ArrayList<>(roles);
    }

    /**
     * Este método se encarga de verificar coincidencia entre los datos ingresados
     * por el usuario y la fuente de datos correspondiente.
     * @param user Se válida asociación lícita entre email y password para autenticación.
     * @param authorities Se determina el nivel de acceso del usuario a los recursos del sistema para autorización.
     * */
    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }


//    validations
    private boolean checkEmptyFields(DataUser dataUser, BindingResult bindingResult) {
        if(dataUser.getName().isEmpty()){
            bindingResult.rejectValue("name", "error.user.name.empty",
                    messageSource.getMessage( "error.field.empty",null, LocaleContextHolder.getLocale()));
        }
        if(dataUser.getLastName().isEmpty()){
            bindingResult.rejectValue("lastName", "error.user.lastName.empty",
                    messageSource.getMessage( "error.field.empty",null, LocaleContextHolder.getLocale()));
        }
        if(dataUser.getEmail().isEmpty()){
            bindingResult.rejectValue("email", "error.user.email.empty",
                    messageSource.getMessage( "error.field.empty",null, LocaleContextHolder.getLocale()));
        }
        if(dataUser.getPassword().isEmpty()){
            bindingResult.rejectValue("password", "error.user.password.empty",
                    messageSource.getMessage( "error.field.empty",null, LocaleContextHolder.getLocale()));
        }
        if(dataUser.getMatchingPassword().isEmpty()){
            bindingResult.rejectValue("matchingPassword", "error.user.matchingPassword.empty",
                    messageSource.getMessage( "error.field.empty",null, LocaleContextHolder.getLocale()));
        }
        return bindingResult.hasErrors();
    }

    private boolean validateEmail(DataUser dataUser, BindingResult bindingResult) {

        if(! bindingResult.hasFieldErrors("email")) {
            if(! emailValidator.isValid(dataUser.getEmail(), null)) {
                bindingResult.rejectValue("email", "error.user.email.invalid",
                        messageSource.getMessage( "error.email.invalid",null, LocaleContextHolder.getLocale()));
            }
            else if(isEmailExist(dataUser.getEmail())) {
                bindingResult.rejectValue("email", "error.user.email.exist",
                        messageSource.getMessage( "error.email.exist",null, LocaleContextHolder.getLocale()));
            }
        }
        return bindingResult.hasErrors();
    }

    private boolean validatePassword(DataUser dataUser, BindingResult bindingResult) {

        if(! bindingResult.hasFieldErrors("password")) {
            if(! passwordValidator.isValid(dataUser.getPassword(), null)) {
                bindingResult.rejectValue("password", "error.user.password.invalid",
                        messageSource.getMessage( "error.password.invalid",null, LocaleContextHolder.getLocale()));
            }
            else if(! dataUser.getPassword().equals(dataUser.getMatchingPassword())) {
                bindingResult.rejectValue("password", "error.user.password.invalid",
                        messageSource.getMessage( "error.password.not-match",null, LocaleContextHolder.getLocale()));
            }
        }
        return bindingResult.hasErrors();
    }




    //    ----- Métodos para test -----
    public Location getDefaultLocation(){
        return Location.builder()
                .street("Calchaquí")
                .number(6720)
                .zipCode("C8291")
                .city("Florencio Varela")
                .state("Buenos Aires")
                .country("Argentina")
                .build();
    }

    public User createUser(String name, String lastName, int age){
        User user = new User(null, name, lastName, age, 10000000, "default@email.com","-", "0", new HashSet<>(Collections.singletonList(UserRole.ROLE_USER)), new Location(), true);
        user.setLocation(getDefaultLocation());
        return user;
    }

    public User createUserAdmin(String name, String lastName, int age){

        User user = new User(null, name, lastName, age, 10000000, "default@email.com", "-", "0", new HashSet<>(Collections.singletonList(UserRole.ROLE_USER)), new Location(), true);
        user.setLocation(getDefaultLocation());
        return user;
    }
//    -----------------------------
}

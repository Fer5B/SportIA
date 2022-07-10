package unaj.ajsw.sportia.controller;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import unaj.ajsw.sportia.model.User;
import unaj.ajsw.sportia.service.UserService;
import javax.annotation.Resource;


@Controller
public class UserController {

    @Resource(name = "userService")
    private UserService userService;

    @GetMapping("/signup--")
    public String signupPage(){
        return "signup";
    }

    @PostMapping("/signup--")
    public String newSignup(Model model, @RequestBody User user){
//        User newUser = userService.createUser(user.getName(), user.getLastName(), user.getAge());
        if(! userService.isEmailExist(user.getEmail())){
            userService.saveUser(user);
            System.out.println("user: "+user.getFullName() + " agregado al sistema!");
            model.addAttribute("users", userService.findAllUsers());
        }
        else
            model.addAttribute("error", true);

        return "redirect:/users";
    }

    @GetMapping("/login---")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/login---")
    public String newLogin(Model model, @RequestParam String username, @RequestParam String password){
        return "login";
    }

    @GetMapping("/users")
    public String getUsers(Model model){
        model.addAttribute("users", userService.findAllUsers());
        return "list-users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(Model model, @PathVariable ObjectId id){
        model.addAttribute("user", userService.findUserById(id));
        return "edit-user";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(Model model, @PathVariable ObjectId id) {
        userService.deleteUserById(id);
        model.addAttribute("users", userService.findAllUsers());
        return "redirect:/users";
    }

}

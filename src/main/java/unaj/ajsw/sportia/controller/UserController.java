package unaj.ajsw.sportia.controller;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import unaj.ajsw.sportia.service.UserService;

import javax.annotation.Resource;

@Controller
public class UserController {

    @Resource(name = "userService")
    private UserService userService;

    @GetMapping("/users")
    public String getUsers(Model model){
        model.addAttribute("users", userService.findAll());
        return "list-users";
    }

    @GetMapping("/edit/{ip}")
    public String editUser(Model model, @PathVariable ObjectId id){
        model.addAttribute("user", userService.findUserById(id));
        return "edit-user";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(Model model, @PathVariable ObjectId id) {
        userService.deleteUserById(id);
        model.addAttribute("users", userService.findAll());
        return "list-users";
    }
}

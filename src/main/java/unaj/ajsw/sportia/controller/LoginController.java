package unaj.ajsw.sportia.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import unaj.ajsw.sportia.dto.DataUser;
import unaj.ajsw.sportia.model.User;
import unaj.ajsw.sportia.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class LoginController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource(name = "userService")
    private UserService userService;


    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/signup")
    public ModelAndView signup(){
        ModelAndView modelAndView = new ModelAndView();
        DataUser user = new DataUser();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @PostMapping("/signup")
    public String createNewUser(@ModelAttribute("user") @Valid DataUser dataUser, BindingResult bindingResult, Model model) {
        LOGGER.debug("Registering user account with data: {}", dataUser);

        userService.registerNewUserAccount(dataUser, bindingResult);

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        model.addAttribute("user", new DataUser());
        return "redirect:/login";
    }


//    @PostMapping("/signup")
//    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid DataUser dataUser,
//                                            HttpServletRequest request, Errors errors) {
//        ModelAndView modelAndView = new ModelAndView();
//
//        if(userService.isEmailExist(dataUser.getEmail())) {
//            modelAndView.addObject("email-exist", true);
//            modelAndView.setViewName("signup");
//            return modelAndView;
//        }
//
//        modelAndView.setViewName("login");
//        return modelAndView;
//    }


    @GetMapping("/dashboard")
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("fullName", "Welcome " + user.getFullName());
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }


}

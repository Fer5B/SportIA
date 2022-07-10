package unaj.ajsw.sportia;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import unaj.ajsw.sportia.service.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class SportIaApplicationTests {
    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void findUserByEmailFromUserService(){
        String email = "admin@email.com";
        Assertions.assertNotNull(userService.findUserByEmail(email), "searching by email from the UserService failed");
    }

    @Test
    void findUserByName(){
        String name = "Juana";
        Assertions.assertFalse(userService.findUsersByName(name).isEmpty(), "find by name from the UserService failed");
    }

    @Test
    void findAllUsers(){
        Assertions.assertEquals(2, userService.findAllUsers().size(), "find all users from the UserService failed");
    }

//    signup
    @Test
    void saveNewUser_WithCorrectInput_thenSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                .accept(MediaType.TEXT_HTML)
                .param("name", "Juan")
                .param("lastName", "Peréz")
                .param("email", "jp@email.com")
                .param("password", "pass")
                .param("matchingPassword", "pass"))
                .andExpect(view().name("login"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}

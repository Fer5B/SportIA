package unaj.ajsw.sportia.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import unaj.ajsw.sportia.model.UserRole;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {  // (2)
        http
            .authorizeRequests()
            .antMatchers("/", "/inicio-de-sesion", "/registro").permitAll() // (3)
            .antMatchers("/css/**", "/js/**", "/img/**").permitAll()
            .antMatchers(HttpMethod.POST, "/clases").hasAnyRole("ROLE_USER", "ROLE_ADMIN")
            .antMatchers("/admin").hasAuthority("ROLE_ADMIN")
            .anyRequest().authenticated() //
            .and()
            .formLogin() // (5)
//            .loginPage("/inicio-de-sesion") // (5)
            .permitAll()
            .and()
            .logout() // (6)
            .permitAll()
            .and()
            .httpBasic(); // (7)
    }
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.builder().username("user")
                .password(passwordEncoder().encode("password"))
                .roles(UserRole.ROLE_USER.name()).build());
        manager.createUser(User.builder().username("admin")
                .password(passwordEncoder().encode("password"))
                .roles(UserRole.ROLE_USER.name(), UserRole.ROLE_ADMIN.name()).build());
        return manager;
    }

    @Bean
    protected PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder(12);
    }
}

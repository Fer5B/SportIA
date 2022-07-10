package unaj.ajsw.sportia.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import unaj.ajsw.sportia.configuration.CustomizeAuthenticationSuccessHandler;
import unaj.ajsw.sportia.service.UserService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;

    @Autowired
    public WebSecurityConfig(CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler) {
        this.customizeAuthenticationSuccessHandler = customizeAuthenticationSuccessHandler;
    }

    @Bean
    public UserDetailsService mongoUserDetails() {
        return new UserService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserDetailsService userDetailsService = mongoUserDetails();
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {  //
        http
            .authorizeRequests()
//                "/classes/recreational", "/classes/sports",
            .antMatchers("/", "home", "/classes/recreational", "/classes/sports", "/login", "/signup", "/perform_login").permitAll()
            .antMatchers( "/classes/create-preference").hasAnyRole("USER", "ADMIN")
            .antMatchers("/classes/create-class", "/dashboard").hasAuthority("ROLE_ADMIN")
            .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
//                .loginProcessingUrl("/perform_login")
                .failureUrl("/login?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(customizeAuthenticationSuccessHandler)
                .permitAll()
            .and()
            .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll()
            .and().exceptionHandling()
            .and().httpBasic()
            .and().csrf().disable();

    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**");
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.builder().username("user")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER").build());
//        manager.createUser(User.builder().username("admin")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER", "ADMIN").build());
//        return manager;
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}

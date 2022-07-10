package unaj.ajsw.sportia.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import unaj.ajsw.sportia.model.UserRole;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomizeAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler { //implements AuthenticationSuccessHandler

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        //set our response to OK status
        response.setStatus(HttpServletResponse.SC_OK);

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (UserRole.ROLE_ADMIN.name().equals(auth.getAuthority())) {
                response.sendRedirect("/dashboard");
            }
            else{
//                response.sendRedirect(request.getContextPath());
//                Redirecciona al usuario a la página que quería ir o a la página segura antes de iniciar sesión
                super.onAuthenticationSuccess(request, response, authentication);
            }
        }
    }

}

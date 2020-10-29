package ru.lis154.SpringBootSecPr.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.lis154.SpringBootSecPr.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    @Autowired
    UserService userService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {


        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        System.out.println("SuccessUserHandler -----" + roles);

        System.out.println("________SECURITY" + roles);
        if (roles.contains("ROLE_USER")) {

            httpServletResponse.sendRedirect("/user");
        } else if (roles.contains("ROLE_ADMIN")){
            httpServletResponse.sendRedirect("/admin");
        }
    }
}

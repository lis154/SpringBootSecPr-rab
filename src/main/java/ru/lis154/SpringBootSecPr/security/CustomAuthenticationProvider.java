package ru.lis154.SpringBootSecPr.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;
import ru.lis154.SpringBootSecPr.Model.Role;
import ru.lis154.SpringBootSecPr.Model.User;
import ru.lis154.SpringBootSecPr.service.UserServiceImpl;

import java.util.Set;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserServiceImpl userService;

    @Override
//    public UsernamePasswordAuthenticationToken authenticate(Authentication auth) throws AuthenticationException {
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {
        String username;
        if (auth.getClass().toString().equals("class org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken")){
            DefaultOidcUser userOAuth = (DefaultOidcUser) auth.getPrincipal();
            username = (String) userOAuth.getAttributes().get("email");
        } else {
            username = auth.getName();
        }
        User user = userService.getByName(username);
       Set<Role> list =  user.getRoles();
        String password = user.getPassword();
        System.out.println("Authentication authenticate   " + username +  "    " + list);
        return new UsernamePasswordAuthenticationToken(username, password, list);

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass));
    }
}

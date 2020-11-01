package ru.lis154.SpringBootSecPr.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import ru.lis154.SpringBootSecPr.Model.Role;
import ru.lis154.SpringBootSecPr.Model.User;
import ru.lis154.SpringBootSecPr.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class GoogleAuthoritiesExtractor implements AuthoritiesExtractor {
    @Autowired
    UserService userService;
    List<GrantedAuthority> GOOGLE_USER_AUTHORITIES
            = AuthorityUtils.commaSeparatedStringToAuthorityList(
            "ROLE_USER");
    List<GrantedAuthority> GOOGLE_ADMIN_AUTHORITIES
            = AuthorityUtils.commaSeparatedStringToAuthorityList(
            "ROLE_ADMIN");
    @Override
    public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
        User user = userService.getByName((String) map.get("email"));
        Set<Role> roles = user.getRoles();
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        if (roles.contains("ROLE_USER")) return GOOGLE_USER_AUTHORITIES;
        else return GOOGLE_ADMIN_AUTHORITIES;

    }
}

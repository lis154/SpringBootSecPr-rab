package ru.lis154.SpringBootSecPr.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService; // сервис, с помощью которого тащим пользователя
    private final SuccessUserHandler successUserHandler; // класс, в котором описана логика перенаправления пользователей по ролям

    @Autowired
    private CustomAuthenticationProvider authProvider;


    public SecurityConfig(@Qualifier("userServiceImpl") UserDetailsService userDetailsService, SuccessUserHandler successUserHandler) {
        this.userDetailsService = userDetailsService;
        this.successUserHandler = successUserHandler;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); // конфигурация для прохождения аутентификации
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    @Bean
    public PrincipalExtractor githubPrincipalExtractor() {
        return new GooglePrincipalExtractor();
    }

    @Bean
    public AuthoritiesExtractor githubAuthoritiesExtractor() {
        return new GoogleAuthoritiesExtractor();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.csrf().disable(); - попробуйте выяснить сами, что это даёт


        http.csrf().disable().authorizeRequests()

                .antMatchers("/user").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')") // разрешаем входить на /user пользователям с ролью User
                .antMatchers("/admin/add").access("hasAuthority('ROLE_ADMIN')")
                .antMatchers("/admin/add/*").access("hasAuthority('ROLE_ADMIN')")
                .antMatchers("/admin/edit").access("hasAuthority('ROLE_ADMIN')")
                .antMatchers("/admin/bootPrim").access("hasAuthority('ROLE_ADMIN')")
                .antMatchers("/admin/edit/*").access("hasAuthority('ROLE_ADMIN')")
                .antMatchers("/admin/delete").access("hasAuthority('ROLE_ADMIN')")
                .antMatchers("/admin").access("hasAuthority('ROLE_ADMIN')")
                .and().formLogin()  // Spring сам подставит свою логин форму
                .successHandler(successUserHandler); // подключаем наш SuccessHandler для перенеправлени
        System.out.println("http-vvod-login");

        http.csrf().disable().authorizeRequests()
                // antMatcher("/**").authorizeRequests()
                //.antMatchers("/").permitAll()
                //.anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/login")
                .and()
                .oauth2Login()
                .successHandler(successUserHandler); // подключаем наш SuccessHandler для перенеправлени

    }


    // Необходимо для шифрования паролей
    // В данном примере не используется, отключен
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }




}

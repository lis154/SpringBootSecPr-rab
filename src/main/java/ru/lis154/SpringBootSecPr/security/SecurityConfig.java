package ru.lis154.SpringBootSecPr.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

//    @Qualifier("oauth2ClientContext")
//    @Autowired()
//    private OAuth2ClientContext oAuthClientContext;
    
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


        http.csrf().disable().authorizeRequests()
               // antMatcher("/**").authorizeRequests()
                //.antMatchers("/").permitAll()
                //.anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/login")
                .and()
                .oauth2Login()
                 .successHandler(successUserHandler); // подключаем наш SuccessHandler для перенеправлени

//        http.csrf().disable().authorizeRequests()
//
//                .antMatchers("/user").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')") // разрешаем входить на /user пользователям с ролью User
//                .antMatchers("/admin/add").access("hasAuthority('ROLE_ADMIN')")
//                .antMatchers("/admin/add/*").access("hasAuthority('ROLE_ADMIN')")
//                .antMatchers("/admin/edit").access("hasAuthority('ROLE_ADMIN')")
//                .antMatchers("/admin/bootPrim").access("hasAuthority('ROLE_ADMIN')")
//                .antMatchers("/admin/edit/*").access("hasAuthority('ROLE_ADMIN')")
//                .antMatchers("/admin/delete").access("hasAuthority('ROLE_ADMIN')")
//                .antMatchers("/admin").access("hasAuthority('ROLE_ADMIN')")
//                .and().formLogin()  // Spring сам подставит свою логин форму
//                .successHandler(successUserHandler); // подключаем наш SuccessHandler для перенеправлени



//                .and().formLogin()  // Spring сам подставит свою логин форму
//                .successHandler(successUserHandler); // подключаем наш SuccessHandler для перенеправления по ролям
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/**")
//                .authenticated();
//    }



    // Необходимо для шифрования паролей
    // В данном примере не используется, отключен
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

//    private Filter ssoFilter() {
//        OAuth2ClientAuthenticationProcessingFilter googleFilter = new OAuth2ClientAuthenticationProcessingFilter(
//                "/login/google");
//        googleFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler());
//        OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(google(), oAuth2ClientContext);
//        googleFilter.setRestTemplate(googleTemplate);
//        CustomUserInfoTokenServices tokenServices = new CustomUserInfoTokenServices(
//                googleResource().getUserInfoUri(), google().getClientId());
//        tokenServices.setRestTemplate(googleTemplate);
//        googleFilter.setTokenServices(tokenServices);
//        tokenServices.setUserDAO(userDAO);
//        tokenServices.setPasswordEncoder(passwordEncoder);
//        return googleFilter;
//    }

//    private OAuth2ClientAuthenticationProcessingFilter oauth2SsoFilter() {
//        ApplicationContext applicationContext = this.getApplicationContext();
//        OAuth2SsoProperties sso = applicationContext.getBean(OAuth2SsoProperties.class);
//        OAuth2RestOperations restTemplate = applicationContext.getBean(UserInfoRestTemplateFactory.class)
//                .getUserInfoRestTemplate();
//        ResourceServerTokenServices tokenServices = applicationContext.getBean(ResourceServerTokenServices.class);
//        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(sso
//                .getLoginPath());
//        filter.setRestTemplate(restTemplate);
//        filter.setTokenServices(tokenServices);
//        filter.setApplicationEventPublisher(applicationContext);
//        //filter.setAuthenticationSuccessHandler(new YourOwnAuthenticationSuccessHandler());
//        return filter;
//    }





}

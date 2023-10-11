package com.woori.bookspring.config;

import com.woori.bookspring.config.oauth.OAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.PrintWriter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final OAuth2UserServiceImpl oAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling(handler -> handler.accessDeniedHandler(new AccessDeniedHandlerImpl()))
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/img/**").permitAll()
                        .requestMatchers("/", "/book", "/ebook", "/user/login" ,"/book/*","/ebook/*","/article/*","/article/*/*","/signup","/emailCheck", "/nicknameCheck","/login").permitAll()
                        .requestMatchers("/admin","/admin/**").hasAnyAuthority("ADMIN","SUPER")
                        .requestMatchers("/user","/user/**",
                                "/order","/order/**",
                                "/cart","/cart/**",
                                "/like","/like/**",
                                "/inven","/inven/**",
                                "/article/*/new","/article/*/*/comment","/article/*/*/comment/**").hasAnyAuthority("USER","SUPER")
                        .requestMatchers("/writer","/writer/**").hasAnyAuthority("WRITER","SUPER")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/user/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .defaultSuccessUrl("/", true).permitAll())
                .logout(out -> out
                        .logoutSuccessUrl("/")
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true))
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService)))
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    //inMemory 기본 계정 생성
    /*@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder.encode("asdf1234")).authorities("SUPER");

    }*/

}

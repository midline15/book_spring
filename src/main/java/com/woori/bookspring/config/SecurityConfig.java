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
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/img/**").permitAll()
                        .requestMatchers("/", "/book", "/ebook", "/user/**","/book/*","/ebook/*","/article/**").permitAll()
                        .requestMatchers("/admin","/admin/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/user","/user/**","/order","/order/**","/cart","/cart/**","/like","/like/**","/inven","/inven/**","/article/*/*/comment","/article/*/*/comment/**").hasAnyAuthority("USER")
                        .requestMatchers("/writer","/writer/**").hasAnyAuthority("WRITER")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/user/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .defaultSuccessUrl("/", true))
                .logout(out -> out
                        .logoutSuccessUrl("/")
                        .logoutUrl("/logout"))
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService)))
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(handler -> handler.accessDeniedHandler(new AccessDeniedHandlerImpl()));
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin@asdf").password(passwordEncoder.encode("asdf")).authorities("ADMIN");

        auth.inMemoryAuthentication()
                .withUser("writer").password(passwordEncoder.encode("asdf")).authorities("WRITER");

        auth.inMemoryAuthentication()
                .withUser("user").password(passwordEncoder.encode("asdf")).authorities("USER");

    }

}

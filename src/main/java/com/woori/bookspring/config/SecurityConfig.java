package com.woori.bookspring.config;

import com.woori.bookspring.config.oauth.OAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OAuth2UserServiceImpl oAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/img/**").permitAll()
                        .requestMatchers("/", "/book", "/ebook", "/user/**","/book/**","ebook/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/user/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true))
                .logout(out -> out
                        .logoutSuccessUrl("/")
                        .logoutUrl("/logout"))
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService)))
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

}

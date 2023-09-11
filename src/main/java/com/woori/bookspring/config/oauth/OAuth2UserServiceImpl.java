package com.woori.bookspring.config.oauth;

import com.woori.bookspring.config.auth.UserDetailsImpl;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuthAttribute attributes = OAuthAttribute.of(registrationId, oAuth2User.getAttributes());

        User user = save(attributes);

        return new UserDetailsImpl(user, oAuth2User.getAttributes());
    }

    private User save(OAuthAttribute attributes){
        User user = userRepository.findById(attributes.getUsername())
                .orElse(User.builder()
                        .username(attributes.getUsername())
                        .password(passwordEncoder.encode(attributes.getPassword()))
                        .oauth(attributes.getOauth())
                        .build()
                );
        return userRepository.save(user);
    }
}

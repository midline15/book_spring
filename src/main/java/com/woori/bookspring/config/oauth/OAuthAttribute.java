package com.woori.bookspring.config.oauth;

import com.woori.bookspring.constant.OAuthType;
import com.woori.bookspring.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OAuthAttribute {
    private Map<String, Object> attributes;
    private String nickname;
    private String email;
    private OAuthType oauth;
    private String password;
    private Role role;

    public static OAuthAttribute of(String registrationId, Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return ofNaver(attributes);
        } else if ("kakao".equals(registrationId)) {
            return ofKakao(attributes);
        }
        return ofGoogle(attributes);
    }

    //WRITER
    private static OAuthAttribute ofGoogle(Map<String, Object> attributes) {
        return OAuthAttribute.builder()
                .nickname(attributes.get("name").toString())
                .email(attributes.get("email")+"_google")
                .password("google123")
                .role(Role.WRITER)
                .oauth(OAuthType.GOOGLE)
                .attributes(attributes)
                .build();
    }

    //ADMIN
    private static OAuthAttribute ofNaver (Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttribute.builder()
                .nickname(response.get("name").toString())
                .email(response.get("email")+"_naver")
                .password("naver123")
                .role(Role.ADMIN)
                .oauth(OAuthType.NAVER)
                .attributes(response)
                .build();
    }

    // USER
    private static OAuthAttribute ofKakao (Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");

        return OAuthAttribute.builder()
                .nickname(((Map<String, Object>)response.get("profile")).get("nickname").toString())
                .email(response.get("email")+"_kakao")
                .password("kakao123")
                .role(Role.USER)
                .oauth(OAuthType.KAKAO)
                .attributes(response)
                .build();
    }
}

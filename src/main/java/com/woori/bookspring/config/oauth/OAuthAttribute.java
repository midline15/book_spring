package com.woori.bookspring.config.oauth;

import com.woori.bookspring.constant.OAuthType;
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
    private String username;
    private String email;
    private OAuthType oauth;
    private String password;

    public static OAuthAttribute of(String registrationId, Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return ofNaver(attributes);
        } else if ("kakao".equals(registrationId)) {
            return ofKakao(attributes);
        }
        return ofGoogle(attributes);
    }

    private static OAuthAttribute ofGoogle(Map<String, Object> attributes) {
        return OAuthAttribute.builder()
                .username(attributes.get("email")+ "_google")
                .email((String) attributes.get("email"))
                .password("google123")
                .oauth(OAuthType.GOOGLE)
                .attributes(attributes)
                .build();
    }

    private static OAuthAttribute ofNaver (Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttribute.builder()
                .username(response.get("email")+"_naver")
                .email((String) response.get("email"))
                .password("naver123")
                .oauth(OAuthType.NAVER)
                .attributes(response)
                .build();
    }
    private static OAuthAttribute ofKakao (Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");

        return OAuthAttribute.builder()
                .username(response.get("email")+"_kakao")
                .email((String) response.get("email"))
                .password("kakao123")
                .oauth(OAuthType.KAKAO)
                .attributes(response)
                .build();
    }
}

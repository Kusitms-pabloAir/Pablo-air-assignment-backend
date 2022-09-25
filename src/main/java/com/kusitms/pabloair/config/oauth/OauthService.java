package com.kusitms.pabloair.config.oauth;

import com.kusitms.pabloair.domain.User;
import com.kusitms.pabloair.dto.LoginResponse;
import com.kusitms.pabloair.repository.UserRepository;
import com.kusitms.pabloair.config.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OauthService {
    private static final String BEARER_TYPE = "Bearer";
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponse loginWithToken(String providerName, String userToken) {
        User user = getUserProfileByToken(providerName, userToken);
        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getId()), user.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getRoles());
        return new LoginResponse(user, accessToken, refreshToken);
    }

    private Map<String, Object> getUserAttributesByToken(String userToken){
        return WebClient.create()
                .get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(userToken))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    private User getUserProfileByToken(String providerName, String userToken){
        if(!providerName.equals("kakao")){
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }

        //사용자 정보
        Map<String, Object> userAttributesByToken = getUserAttributesByToken(userToken);
        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(userAttributesByToken);

        Long kakao_id = kakaoUserInfo.getId();
        String name = kakaoUserInfo.getName();
        String nickName = kakaoUserInfo.getNickName();

        if(userRepository.findById(kakao_id).isPresent()){
            throw new IllegalStateException();  //중복회원 예외
        }

        User user = new User(kakao_id, name, nickName, Collections.singletonList("ROLE_USER") );
        User save = userRepository.save(user);
        return save;
    }

}
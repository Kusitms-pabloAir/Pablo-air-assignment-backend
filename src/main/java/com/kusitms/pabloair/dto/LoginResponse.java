package com.kusitms.pabloair.dto;

import com.kusitms.pabloair.domain.Role;
import com.kusitms.pabloair.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponse {
    private Long id;
    private String name;
    private String nickName;
    private List<String> role;
    private String accessToken;
    private String refreshToken;

    public LoginResponse(User user, String accessToken, String refreshToken){
        this.id = user.getId();
        this.name = user.getName();
        this.nickName = user.getNickName();
        this.role = user.getRoles();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;

    }
}
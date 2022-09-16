package com.kusitms.pabloair.controller;

import com.kusitms.pabloair.dto.LoginResponse;
import com.kusitms.pabloair.dto.UserToken;
import com.kusitms.pabloair.security.oauth.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
public class UserController {

    private final OauthService oauthService;


    @GetMapping("/login/oauth/{provider}")
    public ResponseEntity<LoginResponse> login(@PathVariable String provider, @RequestBody UserToken userToken) {
        LoginResponse loginResponse = oauthService.loginWithToken(provider, userToken);
        return ResponseEntity.ok().body(loginResponse);
    }

}

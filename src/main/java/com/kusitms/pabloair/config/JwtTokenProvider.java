package com.kusitms.pabloair.config;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Autowired
    private final CustomUserDetailService userDetailsService;

    @Value("${jwt.access-token.expire-length}")
    private long accessTokenValidityInMilliseconds;
    @Value("${jwt.refresh-token.expire-length}")
    private long refreshTokenValidityInMilliseconds;
    @Value("${jwt.token.secret-key}")
    private String secretKey;

    public String createAccessToken(String payload, List<String> roles){
        return createToken(payload, roles, accessTokenValidityInMilliseconds);
    }


    public String createRefreshToken(List<String> roles){
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        String generatedString = new String(array, StandardCharsets.UTF_8);
        return createToken(generatedString, roles, refreshTokenValidityInMilliseconds);
    }



    public String createToken(String payload, List<String> roles, long expireLength){
        Claims claims = Jwts.claims().setSubject(payload);
        claims.put("roles", roles);
        Date now = new Date();
        Date validity = new Date(now.getTime() + expireLength);

        return Jwts.builder()
                .setSubject(payload)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .compact();
    }



    public Long getPayload(String token){
        try{
            token = token.substring(token.lastIndexOf(" ")+1);
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            return Long.parseLong(claims.getSubject());
        } catch (JwtException e){
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
    }

    public boolean validateToken(String token){
        try{
            token = token.substring(token.lastIndexOf(" ")+1);
            System.out.println(">>>>>>>validateToken1>>>>>>" + token);
            Claims claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody();

            System.out.println(">>>>>>>claimsJws>>>>>>" + claimsJws);
            return !claimsJws.getExpiration().before(new Date());

        } catch (JwtException | IllegalArgumentException exception){
            System.out.println(">>>>>>>validateToken2>>>>>>" + false);
            return false;
        }
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getPayload(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }


}

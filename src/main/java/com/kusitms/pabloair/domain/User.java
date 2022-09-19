package com.kusitms.pabloair.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User implements UserDetails {
    @Id @GeneratedValue()
    @Column(name = "user_id")
    private Long id;

    private Long kakaoId;
    private String name;
    private String nickName;
    private String accountEmail;
    private String serialNumber;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    //주문 연관관계
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Order> orderList = new ArrayList<>();

    public User(Long kakao_id, String name, String nickName, String accountEmail, List<String> role){
        this.kakaoId = kakao_id;
        this.name = name;
        this.nickName = nickName;
        this.accountEmail = accountEmail;
        this.roles = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

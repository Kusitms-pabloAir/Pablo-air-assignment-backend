package com.kusitms.pabloair.security;

import com.kusitms.pabloair.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService{

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(Long id) throws UsernameNotFoundException {

        return (UserDetails) userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));


    }
}

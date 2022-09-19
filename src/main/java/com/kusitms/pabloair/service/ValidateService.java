package com.kusitms.pabloair.service;

import com.kusitms.pabloair.domain.Order;
import com.kusitms.pabloair.domain.User;
import com.kusitms.pabloair.repository.UserRepository;
import com.kusitms.pabloair.repository.ValidateRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ValidateService {

    private final PasswordEncoder passwordEncoder;
    private final ValidateRepository validateRepository;
    private final UserRepository userRepository;

    public boolean validate(Long userid, String serialNumber) {
        Order order = validateRepository.validate(userid, serialNumber);

        System.out.println(">>>>>>>>>order>>>>>>>" + order.getId());
        if (order == null)
            return false;
        else {
            validateRepository.update(order.getId());
            return true;
        }
    }

    public String getSerialNumber(Long userId) {
        //고유 시리얼넘부 생성하기
        String serialNumber = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Calendar dateTime = Calendar.getInstance();
        serialNumber = sdf.format(dateTime.getTime());

        //yyyymmddhh24missSSS_userid_랜덤문자6개
        serialNumber = serialNumber + "_"  + userId + "_" + RandomStringUtils.randomAlphanumeric(6);

        // 암호화
        String encode = passwordEncoder.encode(serialNumber);

        userRepository.saveWithSerialNumber(userId, encode);

        return encode;
    }
}

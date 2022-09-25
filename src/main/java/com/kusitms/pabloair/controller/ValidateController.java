package com.kusitms.pabloair.controller;

import com.kusitms.pabloair.response.HeaderUtil;
import com.kusitms.pabloair.config.JwtTokenProvider;
import com.kusitms.pabloair.response.DefaultRes;
import com.kusitms.pabloair.response.ResponseMessage;
import com.kusitms.pabloair.response.StatusCode;
import com.kusitms.pabloair.service.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class ValidateController {

    private final ValidateService validateService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/validate")
    public ResponseEntity validate(@RequestParam String serialNumber) {
        boolean validate = validateService.validate(serialNumber);


        if(validate == true)
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.VALIDATE_SUCCESS), HttpStatus.OK);
        else
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.VALIDATE_ERROR), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/serialNumber")
    private ResponseEntity getSerialNumber(HttpServletRequest request) {
        String accessToken = HeaderUtil.getAccessToken(request);
        Long userId = jwtTokenProvider.getPayload(accessToken);

        String serialNumber = validateService.getSerialNumber(userId);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SERIAL_NUMBER_SUCCRSS, serialNumber), HttpStatus.OK);
    }
}

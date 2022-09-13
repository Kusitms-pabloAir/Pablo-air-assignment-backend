package com.kusitms.pabloair.controller;

import com.kusitms.pabloair.dto.ValidateRequestDto;
import com.kusitms.pabloair.response.DefaultRes;
import com.kusitms.pabloair.response.ResponseMessage;
import com.kusitms.pabloair.response.StatusCode;
import com.kusitms.pabloair.service.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ValidateController {

    private final ValidateService validateService;

    @GetMapping("/validate")
    public ResponseEntity validate(@RequestBody ValidateRequestDto requestDto) {
        boolean validate = validateService.validate(requestDto.getSerialNumber());

        if(validate == true)
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.VALIDATE_SUCCESS), HttpStatus.OK);
        else
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.VALIDATE_ERROR), HttpStatus.BAD_REQUEST);
    }

}

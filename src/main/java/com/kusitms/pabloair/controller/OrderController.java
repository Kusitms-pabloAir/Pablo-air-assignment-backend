package com.kusitms.pabloair.controller;

import com.kusitms.pabloair.config.AES256Util;
import com.kusitms.pabloair.config.JwtTokenProvider;
import com.kusitms.pabloair.dto.OrderRequest;
import com.kusitms.pabloair.dto.OrderRequestDto;
import com.kusitms.pabloair.dto.OrderResponseDto;
import com.kusitms.pabloair.dto.SerialNumberDto;
import com.kusitms.pabloair.response.DefaultRes;
import com.kusitms.pabloair.response.HeaderUtil;
import com.kusitms.pabloair.response.ResponseMessage;
import com.kusitms.pabloair.response.StatusCode;
import com.kusitms.pabloair.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final JwtTokenProvider jwtTokenProvider;

    //주문
    @PostMapping("/order")
    public ResponseEntity order(HttpServletRequest request,
                                @RequestBody OrderRequest orderRequest) {
        String accessToken = HeaderUtil.getAccessToken(request);
        Long userId = jwtTokenProvider.getPayload(accessToken);

        Long orderId = orderService.order(userId, orderRequest.getStoreName(), orderRequest.getOrders());

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.ORDER_SUCCESS, orderId), HttpStatus.OK);
    }

    //주문 조회
    @GetMapping("/order")
    public ResponseEntity getOrders(HttpServletRequest request) {
        String accessToken = HeaderUtil.getAccessToken(request);
        Long userId = jwtTokenProvider.getPayload(accessToken);

        List<OrderResponseDto> orders = orderService.getOrders(userId);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.GET_SUCCESS, orders), HttpStatus.OK);
    }

    //검증
    @GetMapping("/order/validate")
    public ResponseEntity validateSerialNumber(@RequestBody SerialNumberDto serialNumber) {
        boolean validate = orderService.validateSerialNumber(serialNumber.getSerialNumber());

        if(validate == true)
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.VALIDATE_SUCCESS), HttpStatus.OK);
        else
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.VALIDATE_ERROR), HttpStatus.BAD_REQUEST);
    }


    //주문별 시리얼넘버
    @GetMapping("/serialNumber/{orderId}")
    public ResponseEntity getSerialNumber(@PathVariable("orderId") Long orderId) {
        String serialNumber = orderService.getSerialNumber(orderId);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SERIAL_NUMBER_SUCCESS, serialNumber), HttpStatus.OK);
    }

}

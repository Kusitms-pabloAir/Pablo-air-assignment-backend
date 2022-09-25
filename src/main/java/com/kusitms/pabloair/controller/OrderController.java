package com.kusitms.pabloair.controller;

import com.kusitms.pabloair.config.JwtTokenProvider;
import com.kusitms.pabloair.dto.OrderRequest;
import com.kusitms.pabloair.dto.OrderRequestDto;
import com.kusitms.pabloair.dto.OrderResponseDto;
import com.kusitms.pabloair.response.DefaultRes;
import com.kusitms.pabloair.response.HeaderUtil;
import com.kusitms.pabloair.response.ResponseMessage;
import com.kusitms.pabloair.response.StatusCode;
import com.kusitms.pabloair.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}

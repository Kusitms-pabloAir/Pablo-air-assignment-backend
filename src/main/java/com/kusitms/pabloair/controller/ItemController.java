package com.kusitms.pabloair.controller;

import com.kusitms.pabloair.dto.ItemDto;
import com.kusitms.pabloair.response.DefaultRes;
import com.kusitms.pabloair.response.ResponseMessage;
import com.kusitms.pabloair.response.StatusCode;
import com.kusitms.pabloair.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    //물품 조회
    @GetMapping("/items")
    public ResponseEntity getItems() {
        List<ItemDto> items = itemService.getItems();

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.GET_SUCCESS, items), HttpStatus.OK);
    }
}

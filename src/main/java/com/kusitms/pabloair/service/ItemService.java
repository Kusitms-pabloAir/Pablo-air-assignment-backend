package com.kusitms.pabloair.service;

import com.kusitms.pabloair.domain.Item;
import com.kusitms.pabloair.dto.ItemDto;
import com.kusitms.pabloair.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    //물품 조회
    public List<ItemDto> getItems() {
        List<Item> list = itemRepository.findAll();

        List<ItemDto> collect = list.stream().map(m -> new ItemDto(m.getId(), m.getName(), m.getPrice()))
                .collect(Collectors.toList());

        return collect;
    }

}

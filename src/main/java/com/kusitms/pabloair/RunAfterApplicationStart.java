package com.kusitms.pabloair;

import com.kusitms.pabloair.domain.Item;
import com.kusitms.pabloair.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RunAfterApplicationStart implements ApplicationRunner {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Item item1 = Item.createItem("set1)88비빔냉삼 set", 23600, 100);
        Item item2 = Item.createItem("set2)을지로분식 set", 14700, 100);
        Item item3 = Item.createItem("set3)808 해장라면 set", 16600, 100);
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);

    }
}

package com.kusitms.pabloair.service;

import com.kusitms.pabloair.domain.Order;
import com.kusitms.pabloair.repository.ValidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateService {

    private final ValidateRepository validateRepository;

    public boolean validate(Long userid, String serialNumber) {
        Order order = validateRepository.validate(serialNumber);
        if (order == null)
            return false;
        else {
            validateRepository.update(order.getId());
            return true;
        }
    }

}

package com.woori.bookspring.service;

import com.woori.bookspring.dto.BillingDto;
import com.woori.bookspring.entity.Billing;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.repository.BillingRepository;
import com.woori.bookspring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class BillingService {

    private final BillingRepository billingRepository;
    private final UserRepository userRepository;

    public void createBilling(BillingDto billingDto, String email){
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        billingRepository.save(Billing.createBilling(billingDto, user));
    }
}

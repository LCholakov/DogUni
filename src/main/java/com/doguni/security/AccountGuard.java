package com.doguni.security;

import com.doguni.model.AccountType;
import com.doguni.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AccountGuard {

    private final UserRepository userRepository;

    public AccountGuard(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isOwner(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) return false;
        return userRepository.findByEmail(authentication.getName())
                .map(u -> u.getAccountType() == AccountType.OWNER)
                .orElse(false);
    }

    public boolean isTrainer(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) return false;
        return userRepository.findByEmail(authentication.getName())
                .map(u -> u.getAccountType() == AccountType.TRAINER)
                .orElse(false);
    }
}
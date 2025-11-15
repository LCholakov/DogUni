package com.doguni.security;

import com.doguni.model.AccountType;
import com.doguni.model.User;
import com.doguni.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public CustomAuthenticationSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String email = authentication.getName();

        String target = "/";
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            AccountType type = user.getAccountType();
            if (type == AccountType.TRAINER) {
                target = "/trainer/home";
            } else {
                target = "/owner/home";
            }
        }

        clearAuthenticationAttributes(request);
        response.sendRedirect(request.getContextPath() + target);
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        }
    }
}
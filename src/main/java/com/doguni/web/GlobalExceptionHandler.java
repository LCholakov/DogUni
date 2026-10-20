package com.doguni.web;

import com.doguni.exception.DogUniException;
import com.doguni.exception.EmailAlreadyExistsException;
//import com.doguni.web.dto.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
//import org.springframework.validation.BeanPropertyBindingResult;
//import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import org.springframework.web.servlet.support.RequestContextUtils;

import java.time.Instant;
import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice(annotations = Controller.class)
public class GlobalExceptionHandler {

//    @ExceptionHandler(EmailAlreadyExistsException.class)
//    public String handleEmailAlreadyExists(EmailAlreadyExistsException ex,
//                                           @ModelAttribute("registerRequest") RegisterRequest form,
//                                           HttpServletRequest request) {
//        var flashMap = RequestContextUtils.getOutputFlashMap(request);
//
//        var binding = new BeanPropertyBindingResult(form, "registerRequest");
//        binding.rejectValue("email", "error.exists", ex.getMessage());
//
//        flashMap.put("registerRequest", form);
//        flashMap.put(BindingResult.MODEL_KEY_PREFIX + "registerRequest", binding);
//
//        return "redirect:/register";
//    }

//    @ExceptionHandler(EmailAlreadyExistsException.class)
//    public ModelAndView handleEmailAlreadyExists(EmailAlreadyExistsException exception,
//                                           @ModelAttribute("registerRequest") RegisterRequest form,
//                                           BindingResult bindingResult) {
//        bindingResult.rejectValue("email", "email.exists", exception.getMessage());
//        return new ModelAndView("register");
//    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public String handleEmailExists(EmailAlreadyExistsException ex,
                                    RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("emailExists", ex.getMessage());
        return "redirect:/register";
    }


    @ExceptionHandler(DogUniException.class)
    public ModelAndView handleDogUni(DogUniException ex, HttpServletRequest req) {
        log.warn("DogUniException at {}: {}", req.getRequestURI(), ex.getMessage());
        return errorView(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), req);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ModelAndView handleNotFound(NoSuchElementException exception, HttpServletRequest request) {
        log.info("Not found at {}: {}", request.getRequestURI(), exception.getMessage());
        return errorView(HttpStatus.NOT_FOUND, "Not Found", exception.getMessage(), request);
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ModelAndView handleAccessDenied(org.springframework.security.access.AccessDeniedException exception, HttpServletRequest request) {
        log.info("Access denied at {}: {}", request.getRequestURI(), exception.getMessage());
        return errorView(HttpStatus.FORBIDDEN, "Forbidden", "You are not allowed to access this resource.", request);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneric(Exception exception, HttpServletRequest request) {
        log.error("Unhandled exception at {}: {}", request.getRequestURI(), exception.getMessage(), exception);
        return errorView(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "Something went wrong. Please try again later.", request);
    }

    private ModelAndView errorView(HttpStatus status, String error, String message, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error", status);
        mav.addObject("timestamp", Instant.now());
        mav.addObject("status", status.value());
        mav.addObject("error", error);
        mav.addObject("message", message);
        mav.addObject("path", request.getRequestURI());
        return mav;
    }
}
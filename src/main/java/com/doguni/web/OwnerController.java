package com.doguni.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OwnerController {
    @GetMapping("/owner/home")
    public String ownerHome() {
        return "ownerHome";
    }
}

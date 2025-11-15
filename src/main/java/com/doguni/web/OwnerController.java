package com.doguni.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OwnerController {

    @PreAuthorize("@accountGuard.isOwner(authentication)")
    @GetMapping("/owner/home")
    public String ownerHome() {
        return "ownerHome";
    }
}

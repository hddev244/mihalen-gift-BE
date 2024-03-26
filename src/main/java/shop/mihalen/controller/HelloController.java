package shop.mihalen.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mihalen.security.AccountPrincipal;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequiredArgsConstructor
public class HelloController {
    @GetMapping("/")
    public String getMethodName() {
        return "Hello world";
    }

    @GetMapping("/secured")
    public String secured(@AuthenticationPrincipal AccountPrincipal principal) {

        return "If you seee this, then you're logged in as user " + principal.getEmail()
            + " User Id : " + principal.getUserId();
    }
    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal AccountPrincipal principal) {

        return "you are ADMIN = " + principal.getEmail()
             + " User Id : " + principal.getUserId() 
            + " User name : " + principal.getUsername();
    }
    
}

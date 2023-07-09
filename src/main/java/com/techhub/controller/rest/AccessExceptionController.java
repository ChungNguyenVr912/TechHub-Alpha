package com.techhub.controller.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class AccessExceptionController {
    @GetMapping("/404")
    public String sendNotFoundPage(){
        return "error/404";
    }
    @GetMapping("/401")
    public String sendUnauthorizedPage(){
        return "login";
    }
    @GetMapping("/403")
    public String sendForbiddenPage(){
        return "error/403";
    }
}

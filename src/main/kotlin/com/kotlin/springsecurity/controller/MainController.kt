package com.kotlin.springsecurity.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Controller
class MainController {

    @GetMapping("/")
    fun root() : String {
        return "redirect:/auth/kakao/login"
    }
}
package com.kotlin.springsecurity.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/main")
class MainController {

    @GetMapping("/hello")
    fun hello(): String {
        return "Hello World"
    }
}
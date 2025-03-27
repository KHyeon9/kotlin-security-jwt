package com.kotlin.springsecurity.controller.user

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
@RequestMapping("/auth")
class KakaoLoginController {
    
    @GetMapping("/kakao/login")
    fun kakaoLogin() : String {
        return "kakaoLogin"
    }

    @GetMapping("/success")
    fun kakaoLoginSuccess(@RequestParam token: String, model: Model) : String {
        // token을 Model에 담아서 HTML로 전달
        model.addAttribute("token", token)
        return "success"  // success.html로 전달
    }
}
package com.kotlin.springsecurity.controller.user

import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class KakaoLoginController {

    @GetMapping("auth/cookie")
    fun getAuthCookie(@CookieValue("Authorization") token: String?) : String {
        return token ?: "쿠키 없음"
    }
}
package com.kotlin.springsecurity.config

import org.springframework.stereotype.Component

@Component
class JwtTokenProvider {

    // 토큰 생성
    fun createToken(userId: String): String {
        return ""
    }
}
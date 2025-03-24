package com.kotlin.springsecurity.config

import com.kotlin.springsecurity.dto.user.UserAccountDto
import com.kotlin.springsecurity.service.UserAccountService
import com.kotlin.springsecurity.util.JwtTokenUtils
import com.kotlin.springsecurity.util.ServiceUtils
import io.jsonwebtoken.Claims
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component

@Component
class JwtTokenProvider(
    private val serviceUtils: ServiceUtils
) {

    @Value("\${jwt.secret-key}")
    private lateinit var secretKey: String
    @Value("\${jwt.token.expired-time-ms}")
    private val expiredTimeMs: Long = 3600 * 24 // 하루로 지정

    // 토큰 생성
    fun createToken(userId: String): String {
        return JwtTokenUtils.createJwtToken(userId, secretKey, expiredTimeMs)
    }

    // 토큰 검증
    fun validateToken(token: String): Boolean {
        return !JwtTokenUtils.isExpired(token, secretKey)
    }

    // 토큰에서 Authentication 객체 생성
    fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        val claims : Claims = JwtTokenUtils.extractClaims(token, secretKey)
        val userId = claims["userId"].toString()

        val userAccountDto : UserAccountDto = UserAccountDto.fromEntity(
            serviceUtils.loadUserByUserId(userId)
        )

        val userDetails = User(
            userAccountDto.username,
            userAccountDto.password,
            userAccountDto.authorities
        )

        return UsernamePasswordAuthenticationToken(userDetails, token, userDetails.authorities)
    }
}
package com.kotlin.springsecurity.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.SecretKey

class JwtTokenUtils {

    // 토큰 종료 확인
    fun isExpired(token: String, key: String): Boolean {
        val expiredDate = extractClaims(token, key).expiration // 토큰과 키로 시간값 가져옴
        return expiredDate.before(Date()) // 만료된 토큰인지 확인
    }

    // claim 추출
    fun extractClaims(token: String, key: String): Claims {
        return Jwts.parser() // JWT 파서 객체 생성 (JWT에서 헤더, 페이로드, 서명 추출에 사용하기 위해)
            .verifyWith(getKey(key)) // 서명 검증에 사용할 키 설정
            .build() // 파서 빌드
            .parseSignedClaims(token) // 토큰을 위에 서명 검증할 키를 통해 파싱
            .payload // 클레임 추출
    }

    // 토큰 생성
    fun createJwtToken(userId: String, key: String): String {
        val now = Date()
        val validity = Date(now.time + 3600 * 24) // 하루 뒤 만료하도록 설정

        return Jwts.builder()
            .claim("userId", userId)
            .issuedAt(now)
            .expiration(validity)
            .signWith(getKey(key))
            .compact()
    }

    // 서명키 생성
    private fun getKey(key: String): SecretKey {
        // Keys.hmacShaKeyFor를 통해서 SecretKeySpec으로 생성하지 않아도됨
        // Jwts.SIG.HS256.key().build().getAlgorithm() 없이도 생성 가능
        // 최소 256비트로 키 길이를 강제함으로 더 안전 및 짧은 키를 사용하면 예외 발생
        return Keys.hmacShaKeyFor(
            key.toByteArray(StandardCharsets.UTF_8)
        )
    }
}
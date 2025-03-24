package com.kotlin.springsecurity.service

import com.kotlin.springsecurity.dto.user.UserAccountDto
import com.kotlin.springsecurity.entity.UserAccount
import com.kotlin.springsecurity.repository.UserAccountRepository
import com.kotlin.springsecurity.util.JwtTokenUtils
import com.kotlin.springsecurity.util.ServiceUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserAccountService(
    private val userAccountRepository: UserAccountRepository,
    private val encoder: BCryptPasswordEncoder,
    private val serviceUtils: ServiceUtils
) {

    @Value("\${jwt.secret-key}")
    private lateinit var secretKey: String

    @Value("\${jwt.token.expired-time-ms}")
    private val expiredTimeMs: Long = 3600 * 24 // 하루로 지정

    // 회원가입
    @Transactional
    fun regist(userId: String, password:String, nickname: String): UserAccountDto {
        // userId 체크
        userAccountRepository.findByUserId(userId)?.let {
            throw RuntimeException("유저 ${userId}는 이미 존재합니다.")
        }

        // 회원가입 진행
        val userAccount = userAccountRepository.save(
            UserAccount(userId, encoder.encode(password), nickname)
        )

        return UserAccountDto.fromEntity(userAccount);
    }

    // 로그인
    fun login(userId: String, password: String): String {
        // 회원가입 체크
        val userAccount = serviceUtils.getUserAccountOrException(userId)

        if (!encoder.matches(password, userAccount.password)) {
            throw RuntimeException("비밀번호가 해당 아이디와 맞지 않습니다.")
        }

        return JwtTokenUtils.createJwtToken(userId, secretKey, expiredTimeMs)
    }
}
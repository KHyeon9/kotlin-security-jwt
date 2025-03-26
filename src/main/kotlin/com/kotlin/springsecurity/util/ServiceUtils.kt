package com.kotlin.springsecurity.util

import com.kotlin.springsecurity.dto.user.UserAccountDto
import com.kotlin.springsecurity.entity.UserAccount
import com.kotlin.springsecurity.repository.UserAccountRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ServiceUtils(
    private val userAccountRepository: UserAccountRepository
) {
    // 유저 정보 조회
    fun getUserAccountOrException(userId: String) : UserAccount {
        val userAccount = userAccountRepository.findByUserId(userId)
            ?: throw UsernameNotFoundException("사용자 ${userId}를 찾지 못했습니다.")
        return userAccount
    }

    // 카카오 로그인에서 사용할 유저 조회 및 없는 경우 카카오 id가 없는 경우 저장
    @Transactional
    fun getOrCreateUser(userId: String, password: String, nickname: String): UserAccountDto {
        val userAccount = userAccountRepository.findByUserId(userId)
            ?: userAccountRepository.save(UserAccount(userId, password, nickname))
        return UserAccountDto.fromEntity(
            userAccount
        )
    }

    // 토큰 필터에서 사용할 유저 조회
    fun loadUserByUserId(userId: String) : UserAccount {
        val userAccount = userAccountRepository.findByUserId(userId)
            ?: throw UsernameNotFoundException("토큰에서 추출한 ${userId}는 존재하지 않습니다.")
        return userAccount
    }
}
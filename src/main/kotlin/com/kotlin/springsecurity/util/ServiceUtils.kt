package com.kotlin.springsecurity.util

import com.kotlin.springsecurity.entity.UserAccount
import com.kotlin.springsecurity.repository.UserAccountRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

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

    // 토큰 필터에서 사용할 유저 조회
    fun loadUserByUserId(userId: String) : UserAccount {
        val userAccount = userAccountRepository.findByUserId(userId)
            ?: throw UsernameNotFoundException("토큰에서 추출한 ${userId}는 존재하지 않습니다.")
        return userAccount
    }
}
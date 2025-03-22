package com.kotlin.springsecurity.controller.response

import com.kotlin.springsecurity.dto.user.UserAccountDto

data class UserResponse(
    val userId: String,
    val nickname: String,
) {
    companion object {
        fun fromDto(userAccountDto: UserAccountDto): UserResponse? {
            return UserResponse(userAccountDto.userId, userAccountDto.nickname)
        }
    }
}
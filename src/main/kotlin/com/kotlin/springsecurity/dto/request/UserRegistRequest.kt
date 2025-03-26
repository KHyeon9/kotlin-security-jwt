package com.kotlin.springsecurity.dto.request

data class UserRegistRequest(
    val userId: String,
    val password: String,
    val nickname: String,
)
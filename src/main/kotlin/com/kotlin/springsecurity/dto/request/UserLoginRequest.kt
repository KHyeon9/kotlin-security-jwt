package com.kotlin.springsecurity.dto.request

data class UserLoginRequest(
    val userId: String,
    val password: String
)

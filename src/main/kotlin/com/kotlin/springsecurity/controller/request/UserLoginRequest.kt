package com.kotlin.springsecurity.controller.request

data class UserLoginRequest(
    val userId: String,
    val password: String
)

package com.kotlin.springsecurity.controller.request

data class UserRegistRequest(
    val userId: String,
    val password: String,
    val nickname: String,
)
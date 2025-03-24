package com.kotlin.springsecurity.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "token is invalid"),
}
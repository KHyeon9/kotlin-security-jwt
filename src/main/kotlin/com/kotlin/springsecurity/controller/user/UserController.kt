package com.kotlin.springsecurity.controller.user

import com.kotlin.springsecurity.dto.request.UserLoginRequest
import com.kotlin.springsecurity.dto.request.UserRegistRequest
import com.kotlin.springsecurity.dto.response.UserLoginResponse
import com.kotlin.springsecurity.dto.response.UserResponse
import com.kotlin.springsecurity.service.UserAccountService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/user")
class UserController(
    private val userAccountService: UserAccountService
) {

    // 회원가입
    @PostMapping("/regist")
    @ResponseStatus(HttpStatus.CREATED)
    fun regist(@RequestBody request: UserRegistRequest): UserResponse? {
        val userAccountDto = userAccountService.regist(
            request.userId,
            request.password,
            request.nickname
        )

        return UserResponse.fromDto(userAccountDto)
    }
    
    // 로그인
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(@RequestBody request: UserLoginRequest): UserLoginResponse? {
        // login token
        val token = userAccountService.login(request.userId, request.password)

        return UserLoginResponse(token)
    }
}
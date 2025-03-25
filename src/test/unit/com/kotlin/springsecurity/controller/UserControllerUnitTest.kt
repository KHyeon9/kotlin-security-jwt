package com.kotlin.springsecurity.controller

import com.kotlin.springsecurity.controller.response.UserResponse
import com.kotlin.springsecurity.controller.user.UserController
import com.kotlin.springsecurity.dto.user.UserAccountDto
import com.kotlin.springsecurity.service.UserAccountService
import com.ninjasquad.springmockk.MockkBean
import config.TestSecurityConfig
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertTrue

@Import(TestSecurityConfig::class)
@WebMvcTest(controllers = [UserController::class])
@AutoConfigureWebTestClient
class UserControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var userAccountServiceMock: UserAccountService

    @DisplayName("회원가입")
    @Test
    fun regist() {
        val userId = "tester"
        val password = "dummypassword"

        val userAccountDto = UserAccountDto(
            userId,
            password
        )

        every {
            userAccountServiceMock.regist(
                userId, password, any()
            )
        } returns userAccountDto

        val response = webTestClient
            .post()
            .uri("/v1/user/regist")
            .bodyValue(userAccountDto)
            .exchange()
            .expectStatus().isCreated
            .expectBody(UserResponse::class.java)
            .returnResult()
            .responseBody

        println("response : $response")

        assertTrue { response!!.userId == userId }
    }
}
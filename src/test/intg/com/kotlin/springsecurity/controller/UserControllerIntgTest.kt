package com.kotlin.springsecurity.controller

import com.kotlin.springsecurity.dto.request.UserLoginRequest
import com.kotlin.springsecurity.dto.request.UserRegistRequest
import com.kotlin.springsecurity.dto.response.UserLoginResponse
import com.kotlin.springsecurity.dto.response.UserResponse
import com.kotlin.springsecurity.repository.UserAccountRepository
import com.kotlin.springsecurity.service.UserAccountService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertTrue

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT // 동적 포트 지정
)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class UserControllerIntgTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var userAccountRepository: UserAccountRepository

    @Autowired
    lateinit var userAccountService: UserAccountService

    @BeforeEach
    fun setup() {
        // 값이 남아 있을 수도 있으므로 모두 삭제
        userAccountRepository.deleteAll()
        
        // 로그인 테스트를 위한 값 삽입
        userAccountService.regist("tester", "dummypassword", "tester")
    }

    // 회원가입 통합 테스트
    @Test
    fun regist() {
        val userRegistRequest = UserRegistRequest(
            "tester1",
            "dummypassword",
            "tester1"
        )

        val response = webTestClient
            .post()
            .uri("/v1/user/regist")
            .bodyValue(userRegistRequest)
            .exchange()
            .expectStatus().isCreated
            .expectBody(UserResponse::class.java)
            .returnResult()
            .responseBody

        assertTrue { response!!.userId == userRegistRequest.userId }
    }
    
    // 로그인 통합 테스트
    @Test
    fun login() {
        val userLoginRequest = UserLoginRequest(
            "tester",
            "dummypassword"
        )

        val response = webTestClient
            .post()
            .uri("/v1/user/login")
            .bodyValue(userLoginRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody(UserLoginResponse::class.java)
            .returnResult()
            .responseBody

        println("respinse : $response")

        assertTrue { response!!.token != null }
    }
}
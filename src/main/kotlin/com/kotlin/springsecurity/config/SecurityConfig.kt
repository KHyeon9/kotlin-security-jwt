package com.kotlin.springsecurity.config

import com.kotlin.springsecurity.config.filter.JwtTokenFilter
import com.kotlin.springsecurity.exception.CustomAuthenticationEntryPoint
import com.kotlin.springsecurity.service.KakaoOAuthUserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider
) {
    // 검사를 하지 않는 주소 리스트
    val whiteList = listOf(
        "/h2-console",
        "/h2-console/**",
        "/v1/user/**",
        "/auth/login/kakao/**",
        "/auth/kakao/login",
        "/images/**",
        "/"
    ).toTypedArray()

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        kakaoOAuthUserService: KakaoOAuthUserService,
    ): SecurityFilterChain {
        return http
                .csrf{ csrf -> csrf.disable() }
                .authorizeHttpRequests { auth ->
                    auth
                        .requestMatchers(*whiteList).permitAll() // h2 콘솔 통과
                        .anyRequest().authenticated() // 모든 인증 검사
                }
                .sessionManagement { session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session 상태 비저장
                }
                .addFilterBefore(
                    JwtTokenFilter(jwtTokenProvider), // jwt 검증 필터
                    UsernamePasswordAuthenticationFilter::class.java // 인증 필터 이전에 jwt 필터 진행
                )
                // oauth 로그인 활성화
                .oauth2Login { oAuth ->
                    oAuth
                        // 인증된 사용자 정보를 요청하고 받아오는 엔드포인트 정의
                        .userInfoEndpoint { userInfo ->
                            userInfo.userService(kakaoOAuthUserService) // 인증된 사용자 정보를 처리하는 서비스
                        }
                }
                .exceptionHandling { handling ->
                    handling.authenticationEntryPoint(
                        CustomAuthenticationEntryPoint() // 토큰 인증 에러 커스텀
                    )
                }
                .headers { it.frameOptions { frame -> frame.disable() } } // h2 콘솔의 ifame 허용
                .build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}
package com.kotlin.springsecurity.config

import com.kotlin.springsecurity.config.filter.JwtTokenFilter
import com.kotlin.springsecurity.exception.CustomAuthenticationEntryPoint
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

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
                .csrf{ csrf -> csrf.disable() }
                .headers { it.frameOptions { frame -> frame.disable() } } // h2 콘솔의 ifame 허용
                .authorizeHttpRequests { auth ->
                    auth
                        .requestMatchers("/h2-console").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/v1/user/login", "/v1/user/regist").permitAll()
                        .requestMatchers("/v1/main/**").authenticated()
                        .anyRequest().permitAll() // 모든 인증 검사
                }
                .sessionManagement { session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session 상태 비저장
                }
                .addFilterBefore(
                    JwtTokenFilter(jwtTokenProvider), // jwt 검증 필터
                    UsernamePasswordAuthenticationFilter::class.java // 인증 필터 이전에 jwt 필터 진행
                )
                .exceptionHandling { handling ->
                    handling.authenticationEntryPoint(
                        CustomAuthenticationEntryPoint()
                    )
                }
                .build()

    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}
package com.kotlin.springsecurity.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
                .csrf{ csrf -> csrf.disable() }
                .authorizeHttpRequests { auth ->
                    auth
                        .anyRequest().permitAll() // 모든 인증 허용
                }
                .httpBasic { auth -> auth.disable() } // http 기본 인증 비활성화
                .build()

    }
}
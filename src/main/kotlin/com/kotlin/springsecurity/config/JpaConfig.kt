package com.kotlin.springsecurity.config

import mu.KLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
class JpaConfig {

    companion object : KLogging()

    @Bean
    fun auditorProvider(): AuditorAware<String> {
        return AuditorAware {
            val auditor = SecurityContextHolder
                .getContext()?.authentication // 인증 정보 가져오기
                ?.takeIf { it.isAuthenticated } // 인증 확인
                ?.name // username을 가져옴
                ?.let { Optional.of(it) } ?: Optional.empty()

            auditor.ifPresent {
                username ->
                logger.info("유저 ${username}을 찾았습니다.")
            }

            if (auditor.isEmpty) {
                logger.warn("유저 ${auditor}를 SecurityContext에서 찾지 못했습니다.")
            }

            auditor
        }
    }
}
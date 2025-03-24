package com.kotlin.springsecurity.config

import com.kotlin.springsecurity.dto.user.UserAccountDto
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
            val auditor = (SecurityContextHolder.getContext()
                ?.authentication
                ?.takeIf { it.isAuthenticated }
                ?.principal as? UserAccountDto)
                ?.username
                ?.let { Optional.of(it) } ?: Optional.empty()

            logger.info("Auditing auditor {}", auditor)

            // null이 아닌 경우
            if (auditor.isPresent) {
                logger.info("유저 ${auditor.get()}을 찾았습니다.")
            }

            // null인 경우
            if (auditor.isEmpty) {
                logger.warn("유저를 SecurityContext에서 찾지 못했습니다.")
            }

            auditor
        }
    }
}
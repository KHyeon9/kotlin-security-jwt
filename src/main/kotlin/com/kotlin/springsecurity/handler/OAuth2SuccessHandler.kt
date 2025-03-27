package com.kotlin.springsecurity.handler

import com.kotlin.springsecurity.config.JwtTokenProvider
import com.kotlin.springsecurity.dto.security.OAuth2UserFromDto
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class OAuth2SuccessHandler(
    private val jwtTokenProvider: JwtTokenProvider
) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val userAccountDto = authentication?.principal as OAuth2UserFromDto
        val token = jwtTokenProvider.createToken(userAccountDto.name)

        val redirectUrl = UriComponentsBuilder.fromPath("/auth/success")
            .queryParam("token", token)
            .build().toUriString()

        response!!.sendRedirect(redirectUrl)
    }
}
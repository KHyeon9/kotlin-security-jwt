package com.kotlin.springsecurity.config.filter

import com.kotlin.springsecurity.config.JwtTokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KLogging
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

class JwtTokenFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {

    companion object : KLogging()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        try {
          val header = request.getHeader(HttpHeaders.AUTHORIZATION) // 헤더의 authorization 가져오기

          // header가 null이거나 token값이 아닌 경우
          if (header == null || !header.startsWith("Bearer ")) {
              logger.error(
                  "Header를 얻는 과정에서 에러 발생. Header가 null이거나 맞지 않습니다. URL: ${request.requestURI}"
              )
              filterChain.doFilter(request, response) // JWT 토큰이 없거나 형식이 맞지 않으면 그냥 다음 필터로 요청을 넘김
              return
          }

          // Bearer 제외 토큰 정보를 가져옴
          val token = header.substring(7)

          // 토큰 만료인지 확인
          if (!jwtTokenProvider.validateToken(token)) {
              logger.error("Jwt 토큰이 만료 되었습니다.")
              filterChain.doFilter(request, response)
              return
          }

          // authentication 토큰에 유저 정보 저장
          val authentication = jwtTokenProvider.getAuthentication(token)

          // 추가적인 사용자 세부 정보를 추가 (IP 등을 추가할 수 있음)
          authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

          // 사용자 정보를 SecurityContextHolder에 추가
          SecurityContextHolder.getContext().authentication = authentication

      } catch (e: RuntimeException) {
          logger.error("JWT 검증 과정 에러 발생 $e")
          filterChain.doFilter(request, response) // 예외가 발생하더라도 요청을 계속해서 다음 필터로 넘김
          return
      }

      filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val excludedUrls = listOf("/h2-console", "/v1/user", "/images", "/auth")
        return excludedUrls.any { request.requestURI.startsWith(it) }
    }
}
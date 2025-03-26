package com.kotlin.springsecurity.service

import com.kotlin.springsecurity.dto.security.KakaoOAuth2Response
import com.kotlin.springsecurity.util.OAuth2UserFromDto
import com.kotlin.springsecurity.util.ServiceUtils
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.util.*

@Service
class KakaoOAuthUserService(
    private val serviceUtils: ServiceUtils,
    private val encoder: PasswordEncoder,
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> { // oauth 인증 처리 서비스

    // 카카오 OAuth2 인증 후 사용자를 처리하는 메서드
    // OAuth2UserRequest는 OAuth2 로그인 요청 정보를 포함하고 있으며,
    // 이 객체를 통해 카카오로부터 인증된 사용자의 데이터를 처리
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        // Spring에서 제공하는 기본적인 OAuth2UserService 구현체로,
        // 실제로 OAuth2 인증 후 사용자의 정보를 loadUser 메서드를 통해 반환
        val delegate = DefaultOAuth2UserService()
        // 카카오에서 반환한 사용자 정보
        val oAuth2User = delegate.loadUser(userRequest)

        val kakaoResponse = KakaoOAuth2Response.from(oAuth2User.attributes)

        // OAuth2 클라이언트 설정에서 정의한 "kakao"와 같은 클라이언트 ID(name)
        // yaml에 설정한 id를 가져옴
        val registrationId = userRequest.clientRegistration.registrationId
        val providerId = kakaoResponse.id.toString() // 카카오에서 보내준 id 값
        val userId = "${registrationId}_${providerId}" // 위 2개의 값을 합쳐서 userid 생성

        // 카카오 로그인시 비밀 번호를 제공하지도 사용하지도 않으므로 유니크 값을 만들어 삽입
        val dummypassword = "" + UUID.randomUUID()

        val userAccountDto = serviceUtils.getOrCreateUser(
            userId,
            encoder.encode(dummypassword),
            kakaoResponse.nickname()
        )

        // UserAccountDto를 OAuth2User로 변환하여 반환
        return OAuth2UserFromDto(userAccountDto)
    }
}
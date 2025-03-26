package com.kotlin.springsecurity.dto.security

data class KakaoOAuth2Response(
    val id: Long,
    val kakaoAccount: KakaoAccount
) {
    // 카카오의 응답을 dto로 변경
    companion object {
        fun from(attributes: Map<String, Any>): KakaoOAuth2Response {
            return KakaoOAuth2Response(
                // id 값을  Long 형으로 변환
                id = attributes["id"] as Long,
                // kakao_account를 KakaoAccount 객체로 변환
                kakaoAccount = KakaoAccount
                    .from(attributes["kakao_account"] as Map<String, Any>)
            )
        }
    }
    
    // email과 nickname 불러오는 매소드
    fun email() = this.kakaoAccount.email
    fun nickname() = this.kakaoAccount.nickname()
}
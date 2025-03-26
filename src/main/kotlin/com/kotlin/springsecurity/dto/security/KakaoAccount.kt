package com.kotlin.springsecurity.dto.security

data class KakaoAccount(
    val email: String?,
    val profile: Profile,
) {
    companion object {
        fun from(attributes: Map<String, Any>): KakaoAccount {
            return KakaoAccount(
                // email을 string으로 변환
                email = attributes["email"] as String?,
                // profile로 값 넘기기
                profile = Profile.from(attributes["profile"] as Map<String, Any>)
            )
        }
    }

    fun nickname(): String = profile?.nickname ?: "Unknown"
}
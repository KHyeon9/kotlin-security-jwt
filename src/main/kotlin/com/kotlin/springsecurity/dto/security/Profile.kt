package com.kotlin.springsecurity.dto.security

data class Profile(
    val nickname: String?,
) {
    companion object {
        // 값에서 nickname값 추출하는 메소드
        fun from(attributes: Map<String, Any>): Profile {
            return Profile(
                attributes["nickname"] as String, // 닉네임 문자열로 추출
            )
        }
    }
}

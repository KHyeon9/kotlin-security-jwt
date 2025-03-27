package com.kotlin.springsecurity.dto.security

import com.kotlin.springsecurity.dto.user.UserAccountDto
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

class OAuth2UserFromDto(
    val userAccountDto: UserAccountDto,
) : OAuth2User {
    override fun getName(): String {
        return userAccountDto.userId
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return mutableMapOf(
            "nickname" to userAccountDto.nickname,
            "role" to userAccountDto.roleType.roleName
        )
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(userAccountDto.roleType.roleName))
    }
}
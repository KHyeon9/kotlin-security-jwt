package com.kotlin.springsecurity.dto.user

import com.kotlin.springsecurity.entity.UserAccount
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime

data class UserAccountDto(
    var userId: String,
    private var password: String,
    var nickname: String = "",
    var roleType: RoleType = RoleType.USER,
    val createdAt: LocalDateTime? = null,
    val createdBy: String? = null
) : UserDetails {

    companion object {
        fun fromEntity(userAccount: UserAccount): UserAccountDto {
            return UserAccountDto(
                userAccount.userId,
                userAccount.password,
                userAccount.nickname,
                userAccount.roleType,
                userAccount.createdAt,
            )
        }
    }

    fun changePassword(newPassword: String) {
        password = newPassword
    }

    override fun getAuthorities(): Collection<GrantedAuthority>
        = listOf( SimpleGrantedAuthority(roleType.roleName))

    override fun getUsername(): String = userId

    override fun getPassword(): String = password
}
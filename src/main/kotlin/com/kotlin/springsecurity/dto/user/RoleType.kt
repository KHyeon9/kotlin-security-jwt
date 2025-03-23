package com.kotlin.springsecurity.dto.user

enum class RoleType(
    val roleName: String,
    val description: String
) {
    USER("ROLE_USER", "사용자"),
    ADMIN("ROLE_ADMIN", "관리자")
}
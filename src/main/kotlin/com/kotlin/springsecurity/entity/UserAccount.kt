package com.kotlin.springsecurity.entity

import com.fasterxml.jackson.annotation.JsonFormat
import com.kotlin.springsecurity.dto.user.RoleType
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "user_account")
data class UserAccount(
    @Id
    @Column(length = 30, name = "user_id")
    var userId: String,
    @Column(name = "password", nullable = false)
    var password: String,
    @Column(name = "nickname", nullable = false)
    var nickname: String,
    @Column(name = "role_type", nullable = false)
    var roleType: RoleType = RoleType.USER,
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {

}
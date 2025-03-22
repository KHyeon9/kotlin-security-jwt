package com.kotlin.springsecurity.repository

import com.kotlin.springsecurity.entity.UserAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAccountRepository : JpaRepository<UserAccount, Long> {
    fun findByUserId(userId: String): UserAccount?
}
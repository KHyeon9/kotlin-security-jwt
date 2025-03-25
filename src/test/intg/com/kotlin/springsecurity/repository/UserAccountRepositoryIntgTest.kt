package com.kotlin.springsecurity.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import userAccountEntity
import kotlin.test.assertEquals

@DataJpaTest
@ActiveProfiles("test")
class UserAccountRepositoryIntgTest {

    @Autowired
    lateinit var userAccountRepository: UserAccountRepository

    @Test
    fun findByUserId() {
        val userAccount = userAccountEntity("tester", "dummypassword")
        userAccountRepository.save(userAccount)

        val findUserAccount = userAccountRepository.findByUserId("tester")

        assertEquals(userAccount.userId, findUserAccount?.userId)
    }
}
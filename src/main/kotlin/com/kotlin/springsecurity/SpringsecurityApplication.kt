package com.kotlin.springsecurity

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class SpringsecurityApplication

fun main(args: Array<String>) {
	runApplication<SpringsecurityApplication>(*args)
}

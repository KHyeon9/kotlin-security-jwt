plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.kotlin"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Web
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Security
	implementation("org.springframework.boot:spring-boot-starter-security")

	// Devtools
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// Kotlin
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// DB
	runtimeOnly("com.h2database:h2")

	// JPA
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// JWT
	implementation("io.jsonwebtoken:jjwt-api:0.12.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")

	// Log
	implementation("io.github.microutils:kotlin-logging:3.0.5")

	// Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

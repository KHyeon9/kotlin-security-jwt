package com.kotlin.springsecurity.entity.auditing

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

// audidingFields를 통해서 생성, 수정을 감지하여 시간 입력
@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass // 이 클래스를 상속받은 엔터티들이 해당 필드를 가질 수 있음
abstract class AuditingFields {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false) // updatable false는 한번 기록 되면 변경 불가
    open var createdAt: LocalDateTime? = null // 생성 시간
        protected set // 값을 직접 변경 못하도록 setter를 protect로 제한

    @CreatedBy
    @Column(nullable = false, length = 100)
    open var createdBy: String? = null // 생성자
        protected set
}
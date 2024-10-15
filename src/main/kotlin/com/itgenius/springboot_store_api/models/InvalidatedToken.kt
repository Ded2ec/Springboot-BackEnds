package com.itgenius.springboot_store_api.models

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "invalidated_tokens")
data class InvalidatedToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val token: String,

    @Column(name = "expiration_time", nullable = false)
    val expirationTime: LocalDateTime
)


package com.itgenius.springboot_store_api.security

import com.itgenius.springboot_store_api.models.InvalidatedToken
import com.itgenius.springboot_store_api.repository.InvalidatedTokenRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TokenStore(
    private val invalidatedTokenRepository: InvalidatedTokenRepository
) {

    fun invalidateToken(token: String, expirationTime: Long) {
        val expirationDateTime = LocalDateTime.now().plusSeconds(expirationTime / 1000)
        val invalidatedToken = InvalidatedToken(token = token, expirationTime = expirationDateTime)
        invalidatedTokenRepository.save(invalidatedToken)
    }

    fun isTokenInvalidated(token: String): Boolean {
        return invalidatedTokenRepository.findByToken(token) != null
    }
}
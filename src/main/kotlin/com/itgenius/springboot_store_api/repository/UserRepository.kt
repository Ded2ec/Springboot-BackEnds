package com.itgenius.springboot_store_api.repository

import com.itgenius.springboot_store_api.models.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
}
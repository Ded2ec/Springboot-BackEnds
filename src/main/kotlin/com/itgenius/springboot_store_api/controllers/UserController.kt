package com.itgenius.springboot_store_api.controllers

import com.itgenius.springboot_store_api.models.LoginModel
import com.itgenius.springboot_store_api.models.RegisterModel
import com.itgenius.springboot_store_api.models.ResponseModel
import com.itgenius.springboot_store_api.security.TokenStore
import com.itgenius.springboot_store_api.services.UserService
import com.itgenius.springboot_store_api.utils.JwtUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@Tag(name = "Authenticate", description = "Authenticate API")
@RestController
@RequestMapping("/api/v1/authenticate")
class UserController(
    private val userService: UserService,
    private val jwtUtil: JwtUtil,
    private val passwordEncoder: PasswordEncoder,
    private val tokenStore: TokenStore
) {

    // ฟังก์ชันสำหรับการลงทะเบียน User
    // POST /api/authenticate/register-user
    @Operation(summary = "Register user", description = "Register user to database")
    @PostMapping("/register-user")
    fun registerUser(@RequestBody model: RegisterModel): ResponseEntity<ResponseModel> {
        return try {
            val user = userService.registerUser(model.username, model.email, model.password)
            ResponseEntity.ok(ResponseModel("Success", "User registered successfully"))
        } catch (e: IllegalStateException) {
            ResponseEntity.badRequest().body(ResponseModel("Error", e.message ?: "Registration failed"))
        } catch (e: IllegalStateException) {
            ResponseEntity.internalServerError().body(ResponseModel("Error", e.message ?: "Registration failed due to system configuration"))
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body(ResponseModel("Error", "User creation failed: ${e.message}"))
        }
    }

    // ฟังก์ชันสำหรับการลงทะเบียน Manager
    // POST /api/authenticate/register-manager
    @Operation(summary = "Register manager", description = "Register manager to database")
    @PostMapping("/register-manager")
    fun registerManager(@RequestBody model: RegisterModel): ResponseEntity<ResponseModel> {
        return try {
            val user = userService.registerManager(model.username, model.email, model.password)
            ResponseEntity.ok(ResponseModel("Success", "Manager registered successfully"))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(ResponseModel("Error", e.message ?: "Registration failed"))
        } catch (e: IllegalStateException) {
            ResponseEntity.internalServerError().body(ResponseModel("Error", e.message ?: "Registration failed due to system configuration"))
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body(ResponseModel("Error", "User creation failed: ${e.message}"))
        }
    }

    // ฟังก์ชันสำหรับการลงทะเบียน Admin
    // POST /api/authenticate/register-admin
    @Operation(summary = "Register admin", description = "Register admin to database")
    @PostMapping("/register-admin")
    fun registerAdmin(@RequestBody model: RegisterModel): ResponseEntity<ResponseModel> {
        return try {
            val user = userService.registerAdmin(model.username, model.email, model.password)
            ResponseEntity.ok(ResponseModel("Success", "Admin registered successfully"))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(ResponseModel("Error", e.message ?: "Registration failed"))
        } catch (e: IllegalStateException) {
            ResponseEntity.internalServerError().body(ResponseModel("Error", e.message ?: "Registration failed due to system configuration"))
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body(ResponseModel("Error", "User creation failed: ${e.message}"))
        }
    }

    // ฟังก์ชันสำหรับการ login
    @Operation(summary = "Login", description = "Login to system and get JWT token")
    // POST /api/authenticate/login
    @PostMapping("/login")
    fun login(@RequestBody model: LoginModel): ResponseEntity<ResponseModel> {
        val user = userService.findByUsername(model.username)

        return if (user != null && passwordEncoder.matches(model.password, user.password)) {

            val roles = user.roles.map { it.roleName.name }
            val token = jwtUtil.generateToken(user.username, roles)
            val expiration = jwtUtil.getExpirationDateFromToken(token)

            val data = mapOf(
                "token" to token,
                "expiration" to expiration,
                "userName" to user.username,
                "email" to user.email,
                "roles" to roles
            )

            ResponseEntity.ok(ResponseModel(
                "Success", "Login successful", data
            ))

        } else {
            ResponseEntity.status(401).body(ResponseModel("Error", "Invalid username or password"))
        }
    }

    // ฟังก์ชันสำหรับการ logout
    @Operation(summary = "Logout", description = "Logout from the system")
    // POST /api/authenticate/logout
    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") authorizationHeader: String?): ResponseEntity<ResponseModel> {
        val auth: Authentication? = SecurityContextHolder.getContext().authentication
        if (auth != null && authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            val token = authorizationHeader.substring(7)
            tokenStore.invalidateToken(token, 3600)  // กำหนดเวลา expirationTime เป็น 1 ชั่วโมง (3600 วินาที)
            SecurityContextHolder.clearContext()
        }
        return ResponseEntity.ok(ResponseModel("Success", "Logged out successfully"))
    }

}
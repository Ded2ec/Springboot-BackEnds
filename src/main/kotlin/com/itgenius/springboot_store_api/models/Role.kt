package com.itgenius.springboot_store_api.models

import jakarta.persistence.*

@Entity
@Table(name = "roles")
data class Role(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    val roleName: RoleName,

    @ManyToMany(mappedBy = "roles")
    val users: MutableList<User> = mutableListOf()
)

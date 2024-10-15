package com.itgenius.springboot_store_api.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class ProductCategoryDTO(
    val id: Int,
    val productName: String?,
    val unitPrice: BigDecimal?,
    val unitInStock: Int?,
    val productPicture: String?,
    val categoryId: Int,
    val categoryName: String?,
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime?
)

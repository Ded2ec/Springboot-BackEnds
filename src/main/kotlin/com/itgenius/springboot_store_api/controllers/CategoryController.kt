package com.itgenius.springboot_store_api.controllers

import com.itgenius.springboot_store_api.models.Category
import com.itgenius.springboot_store_api.services.CategoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER') or hasAuthority('USER')")
@Tag(name = "Categories", description = "APIs for managing categories")
@RestController
@RequestMapping("/api/v1/categories")
class CategoryController(private val categoryService: CategoryService) {

    // ฟังก์ชันสำหรับการดึงข้อมูล Category ทั้งหมด
    // GET /api/categories
    @Operation(summary = "Get all categories", description = "Get all categories from database")
    @GetMapping
    fun getCategories(): ResponseEntity<List<Category>> {
        val categories = categoryService.getAllCategories()
        return ResponseEntity.ok(categories)
    }

    // ฟังก์ชันสำหรับการดึงข้อมูล Category ตาม ID
    // GET /api/categories/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID", description = "Get category by ID from database")
    fun getCategory(@PathVariable id: Int): ResponseEntity<Category> {
        val category = categoryService.getCategoryById(id)
        return if (category.isPresent) {
            ResponseEntity.ok(category.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // ฟังก์ชันสำหรับการเพิ่มข้อมูล Category
    // POST /api/categories
    // @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    @Operation(summary = "Add new category", description = "Add new category to database")
    @PostMapping
    fun addCategory(@RequestBody category: Category): ResponseEntity<Category> {
        val savedCategory = categoryService.addCategory(category)
        return ResponseEntity.ok(savedCategory)
    }

    // ฟังก์ชันสำหรับการแก้ไขข้อมูล Category
    // PUT /api/categories/{id}
    @Operation(summary = "Update category by ID", description = "Update category by ID from database")
    @PutMapping("/{id}")
    fun updateCategory(@PathVariable id: Int, @RequestBody category: Category): ResponseEntity<Category> {
        val updatedCategory = categoryService.updateCategory(id, category)
        return if (updatedCategory.isPresent) {
            ResponseEntity.ok(updatedCategory.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // ฟังก์ชันสำหรับการลบข้อมูล Category
    // DELETE /api/categories/{id}
    @Operation(summary = "Delete category by ID", description = "Delete category by ID from database")
    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Int): ResponseEntity<Category> {
        val deletedCategory = categoryService.deleteCategory(id)
        return if (deletedCategory.isPresent) {
            ResponseEntity.ok(deletedCategory.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

}
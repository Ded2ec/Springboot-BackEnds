package com.itgenius.springboot_store_api.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        // กำหนดการเข้าถึงไฟล์จากโฟลเดอร์ /uploads
        registry.addResourceHandler("/uploads/**")
            .addResourceLocations("file:./uploads/")
            .setCachePeriod(0) // ปิดการ Cache เพื่อให้สามารถโหลดไฟล์ใหม่ได้เสมอ
    }
}
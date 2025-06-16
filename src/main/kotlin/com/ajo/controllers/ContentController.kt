package com.ajo.controllers

import com.ajo.service.LegalTextsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ajo")
class ContentController(private val legalTextsService: LegalTextsService) {

    @GetMapping("/agreement")
    fun getAgreement(): Map<String, Any?> {
        val legalText = legalTextsService.findAll().firstOrNull()

        val content = legalText?.agreementContent ?: ""

        return mapOf(
            "seo" to mapOf(
                "meta" to mapOf(
                    "title" to "Пользовательское соглашение",
                    "ogTitle" to "Пользовательское соглашение",
                    "description" to "Это описание для страницы Пользовательское соглашение",
                    "ogDescription" to "Это описание для страницы Пользовательское соглашение",
                    "ogImage" to "/images/limkorm.jpg",
                    "twitterCard" to "summary_large_image",
                    "robots" to "noindex"
                ),
                "H1" to "Пользовательское соглашение",
                "content" to null
            ),
            "schemaOrg" to emptyList<Any>(),
            "content" to content
        )
    }
    @GetMapping("/politika")
    fun getPolitika(): Map<String, Any?> {
        val legalText = legalTextsService.findAll().firstOrNull()

        val content = legalText?.politikaContent ?: ""

        return mapOf(
            "seo" to mapOf(
                "meta" to mapOf(
                    "title" to "Пользовательское соглашение",
                    "ogTitle" to "Пользовательское соглашение",
                    "description" to "Это описание для страницы Пользовательское соглашение",
                    "ogDescription" to "Это описание для страницы Пользовательское соглашение",
                    "ogImage" to "/images/limkorm.jpg",
                    "twitterCard" to "summary_large_image",
                    "robots" to "noindex"
                ),
                "H1" to "Пользовательское соглашение",
                "content" to null
            ),
            "schemaOrg" to emptyList<Any>(),
            "content" to content
        )
    }
    @GetMapping("/rules")
    fun getRules(): Map<String, Any?> {
        val legalText = legalTextsService.findAll().firstOrNull()

        val content = legalText?.rulesContent ?: ""

        return mapOf(
            "seo" to mapOf(
                "meta" to mapOf(
                    "title" to "Пользовательское соглашение",
                    "ogTitle" to "Пользовательское соглашение",
                    "description" to "Это описание для страницы Пользовательское соглашение",
                    "ogDescription" to "Это описание для страницы Пользовательское соглашение",
                    "ogImage" to "/images/limkorm.jpg",
                    "twitterCard" to "summary_large_image",
                    "robots" to "noindex"
                ),
                "H1" to "Пользовательское соглашение",
                "content" to null
            ),
            "schemaOrg" to emptyList<Any>(),
            "content" to content
        )
    }
    @GetMapping("/layout")
    fun getLayout(): ResponseEntity<Map<String, String>> {
        val legalText = legalTextsService.findAll().firstOrNull()
        val email = legalText?.email ?: "support@example.com"
        return ResponseEntity.ok(mapOf("email" to email))
    }
}
    
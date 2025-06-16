package com.ajo.controllers

import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@RestController
@RequestMapping("/ajo/images")
class ImageController {
    private val uploadDir: Path = Paths.get("/home/l/limkorm/fest.ajo-pet.ru/public_html/upload")
    init {
        Files.createDirectories(uploadDir)
    }

    fun uploadImage(file: MultipartFile,filename: String): ResponseEntity<String> {
        if (file.isEmpty) {
            return ResponseEntity("Файл пустой", HttpStatus.BAD_REQUEST)
        }

        val targetLocation = uploadDir.resolve(filename)
        file.transferTo(targetLocation)

        return ResponseEntity("Файл $filename успешно загружен", HttpStatus.OK)
    }

    @GetMapping("/{filename:.+}")
    fun getImage(@PathVariable filename: String): ResponseEntity<Resource> {
        val filePath = uploadDir.resolve(filename).normalize()
        val resource = FileSystemResource(filePath)

        return if (resource.exists()) {
            ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // или определить по расширению
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"$filename\"")
                .body(resource)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{filename:.+}")
    fun deleteImage(@PathVariable filename: String): ResponseEntity<String> {
        val file = uploadDir.resolve(filename).toFile()
        return if (file.exists() && file.delete()) {
            ResponseEntity("Файл удалён", HttpStatus.OK)
        } else {
            ResponseEntity("Файл не найден", HttpStatus.NOT_FOUND)
        }
    }
}

package com.ajo.controllers

import com.ajo.service.FaqItemsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ajo")
class FiiController(
    private val faqService: FaqItemsService
) {

    @GetMapping("/home/")
    fun getFiiPage(): ResponseEntity<Map<String, Any>> {
        val response = mapOf(
            "seo" to mapOf(
                "meta" to mapOf(
                    "title" to "Выигрывай призы от Ajo",
                    "ogTitle" to "Выигрывай призы от Ajo",
                    "description" to "Это описание для страницы Главная страница",
                    "ogDescription" to "Это описание для страницы Главная страница",
                    "ogImage" to "/images/limkorm.jpg",
                    "twitterCard" to "summary_large_image",
                    "robots" to "noindex"
                ),
                "H1" to "Выигрывай призы от Ajo",
                "content" to null
            ),
            "schemaOrg" to emptyList<Any>(),
            "homeScreen" to mapOf(
                "image" to mapOf(
                    "url" to "/images/home/feed-packaging.png",
                    "alt" to "Упаковки корма для животных"
                ),
                "description" to "Каждый питомец нуждается в особом рационе. Именно поэтому AJO® разработал корма, которые учитывают особенности разных пород на различных этапах жизни",
                "period" to "Период проведения акции – с 15 июня по 15 июля 2025 года."
            ),
            "steps" to mapOf(
                "title" to "Как участвовать?",
                "list" to listOf(
                    mapOf(
                        "id" to 1,
                        "image" to mapOf(
                            "url" to "/images/home/step-1.png",
                            "alt" to "Упаковка корма для животных"
                        ),
                        "number" to 1,
                        "label" to "Купи",
                        "description" to "любую упаковку корма AJO"
                    ),
                    mapOf(
                        "id" to 2,
                        "image" to mapOf(
                            "url" to "/images/home/step-2.png",
                            "alt" to "Кассовый чек"
                        ),
                        "number" to 2,
                        "label" to "Загружай",
                        "description" to "чеки на сайт с 15 июня по 15 июля 2025"
                    ),
                    mapOf(
                        "id" to 3,
                        "image" to mapOf(
                            "url" to "/images/home/step-3.png",
                            "alt" to "Коробка с подарком"
                        ),
                        "number" to 3,
                        "label" to "Участвуй",
                        "description" to "в розыгрыше призов"
                    )
                )
            ),
            "prizes" to mapOf(
                "title" to "Участвуй в розыгрыше и выигрывай",
                "stickers" to mapOf(
                    "image" to mapOf(
                        "url" to "stickers.png",
                        "alt" to "Стикеры для Telegram"
                    ),
                    "label" to "Получи стикеры для телеграма",
                    "description" to "Отсканируй чек и получи приветственный стикерпак AJO в подарок!"
                ),
                "list" to listOf(
                    mapOf(
                        "id" to 1,
                        "label" to "Билет на VK fest",
                        "image" to mapOf(
                            "url" to "/images/prizes/vkfest.svg",
                            "alt" to "Билет на VK fest"
                        )
                    ),
                    mapOf(
                        "id" to 2,
                        "label" to "Фирменный мерч",
                        "image" to mapOf(
                            "url" to "/images/prizes/jersey.svg",
                            "alt" to "Фирменный мерч"
                        )
                    ),
                    mapOf(
                        "id" to 3,
                        "label" to "И другие подарки",
                        "image" to mapOf(
                            "url" to "/images/prizes/surprize-white.svg",
                            "alt" to "Иконка коробки с сюрпризом"
                        )
                    )
                )
            ),
            "faq" to mapOf(
                "title" to "Вопрос-ответ",
                "list" to faqService.getFaqList()
            )
        )

        return ResponseEntity.ok(response)
    }
}

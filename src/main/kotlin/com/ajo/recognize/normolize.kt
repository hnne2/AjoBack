package com.ajo.recognize

import org.apache.commons.text.similarity.LevenshteinDistance

val garbageWords = listOf("акция", "скидка", "промо", "подарок", "новинка")


val levenshtein = LevenshteinDistance()
fun normalize(text: String): String {
    return text
        .lowercase()
        .let(::transliterate)
        .let(::unifyUnits)
        .let(::removeGarbage)
        .replace("[^а-яa-z0-9]+".toRegex(), " ")
        .replace("\\s+".toRegex(), " ")
        .trim()
}


fun similarity(s1: String, s2: String): Double {
    val norm1 = normalize(s1)
    val norm2 = normalize(s2)

    val distance = levenshtein.apply(norm1, norm2)
    val maxLen = maxOf(norm1.length, norm2.length)
    if (maxLen == 0) return 1.0
    return 1.0 - distance.toDouble() / maxLen
}

fun removeGarbage(text: String): String {
    var cleaned = text
    for (word in garbageWords) {
        cleaned = cleaned.replace("\\b$word\\b".toRegex(), "")
    }
    return cleaned
}
fun unifyUnits(text: String): String {
    return text
        .replace("грамм(ов)?".toRegex(), "г")
        .replace("\\bг\\b".toRegex(), "г")
        .replace("кг", "000г")         // 1 кг = 1000г (приближение)
        .replace("0\\.\\d+кг".toRegex()) {
            val value = it.value.replace("кг", "").toDouble()
            "${(value * 1000).toInt()}г"
        }
}
fun transliterate(text: String): String {
    return text
        .replace("айо", "ajo")
        .replace("aйо", "ajo")
        .replace("айо", "ajo")
        .replace("айё", "ajo")
        .replace("айо", "ajo")
        .replace("ajd", "ajo")
}



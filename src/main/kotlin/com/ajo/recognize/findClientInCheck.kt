package com.ajo.recognize

import com.ajo.recognize.model.ClientInfo
import com.ajo.recognize.model.RecognizeCheck

fun findClientInCheck(recognizeCheck: RecognizeCheck, clients: List<ClientInfo>): Boolean {
    for (client in clients) {
        if (client.inn==recognizeCheck.inn && recognizeCheck.name.isNullOrEmpty()) {
            return true
        }
    }
    return false
}
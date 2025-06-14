package com.ajo.recognize

import com.ajo.recognize.model.ClientInfo
import com.ajo.recognize.model.RecognizeCheck
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File

fun readClientsFromExcel(file: File): List<ClientInfo> {
    val clients = mutableListOf<ClientInfo>()
    val workbook = WorkbookFactory.create(file)
    val sheet = workbook.getSheetAt(0)

    for (row in sheet.drop(1)) { // Пропускаем заголовок
        val clientId = row.getCell(0)?.toString()?.trim() ?: ""
        val name = row.getCell(1)?.toString()?.trim() ?: ""
        val inn = row.getCell(3)?.toString()?.trim() ?: ""
        clients.add(ClientInfo(name, inn,clientId))
    }

    workbook.close()
    return clients
}


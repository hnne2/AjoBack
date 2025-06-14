package com.ajo.recognize

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File


fun readProductsFromExcel(file: File): List<String> {
    val workbook = XSSFWorkbook(file)
    val sheet = workbook.getSheetAt(1) // первая страница
    val products = mutableListOf<String>()
    for (row in sheet.drop(1)) { // пропускаем заголовок
        val cell = row.getCell(3) // колонка "Номенклатура" (индекс 3)
        val value = cell?.stringCellValue?.trim()
        if (!value.isNullOrBlank() && value != "\\") {
            products.add(value)
        }
    }
    workbook.close()
    return products
}

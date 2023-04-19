package com.example.searchmapuniversity.data.parser

import com.example.searchmapuniversity.models.domain.yandex.UniversityInfoItem
import com.example.searchmapuniversity.models.domain.yandex.UniversityInfoList
import com.example.searchmapuniversity.utils.FileCaching
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream
import java.io.InputStream
import javax.inject.Inject

class UniversityParser @Inject constructor(): XlsxParser<UniversityInfoList> {

    private val universityInfoList: ArrayList<UniversityInfoItem> = ArrayList()

    override suspend fun parse(stream: InputStream): UniversityInfoList {
        val universityInfoPath = FileCaching.save(stream, "universities_")
        val fis = FileInputStream(universityInfoPath)
        val myWorkBook = XSSFWorkbook(fis)
        val numOfSheets = myWorkBook.numberOfSheets

        val contentStringBuilder = StringBuilder("")
        loop@ for (sheet in 0 until 1) {
            var isHeader = true
            val curSheet = myWorkBook.getSheetAt(sheet)
            val rowIterator: Iterator<Row> = curSheet.iterator()
            while (rowIterator.hasNext()) {
                if (isHeader){
                    isHeader = false
                    rowIterator.next()
                    continue
                }
                val row = rowIterator.next()
                val cellIterator = row.cellIterator()
                while (cellIterator.hasNext()) {
                    val cell = cellIterator.next()
                    when(cell.cellType){
                        Cell.CELL_TYPE_STRING -> {
                            contentStringBuilder.append(cell.stringCellValue).append("$#")
                        }
                        Cell.CELL_TYPE_BLANK -> {
                            continue@loop
                        }
                    }
                }
                val rowInfo = contentStringBuilder.split("$#")
                if (rowInfo.size > 1) {
                    universityInfoList.add(
                        UniversityInfoItem(
                            abbreviation = rowInfo[0],
                            name = rowInfo[1],
                            logo = rowInfo[2],
                            address = rowInfo[3],
                            lat = rowInfo[4].split(", ").first().toDouble(),
                            lon = rowInfo[4].split(", ").last().toDouble(),
                            site = rowInfo[5],
                            like = 0
                        )
                    )
                }
                contentStringBuilder.setLength(0)
            }
        }
        return UniversityInfoList(universities = universityInfoList)
    }
}
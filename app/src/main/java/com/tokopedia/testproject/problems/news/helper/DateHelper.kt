package com.tokopedia.testproject.problems.news.helper

import java.lang.Exception
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    @JvmStatic
    fun convertToIndoTime(time: String) : String{
        return try {
            val dateSplit = time.split("T")
            val mountName = DateFormatSymbols().months

            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val dateResult = dateFormat.parse(dateSplit[0])

            val calender = Calendar.getInstance()
            calender.time = dateResult

            "${calender.get(Calendar.DAY_OF_MONTH)} ${mountName[calender.get(Calendar.MONTH)]} ${calender.get(Calendar.YEAR)}"

        }catch (e: Exception){
            ""
        }
    }
}
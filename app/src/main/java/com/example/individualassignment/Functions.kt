package com.example.individualassignment

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Functions {

    private var BITMAP_SCALE = 1.2f
    private var BLUR_RADIUS = 18.5f

    fun blur(image: Bitmap, context: Context?, small: Boolean): Bitmap {

        if(small) BITMAP_SCALE = 0.7f
        val width = Math.round(image.width * BITMAP_SCALE)
        val height = Math.round(image.height * BITMAP_SCALE)

        val inputBitmap = Bitmap.createScaledBitmap(image, width, height, false)
        val outputBitmap = Bitmap.createBitmap(inputBitmap)

        val rs = RenderScript.create(context)
        val theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
        val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)
        theIntrinsic.setRadius(BLUR_RADIUS)
        theIntrinsic.setInput(tmpIn)
        theIntrinsic.forEach(tmpOut)
        tmpOut.copyTo(outputBitmap)

        return outputBitmap
    }

    fun getLastYear(): ArrayList<String> {
        val curDate = Calendar.getInstance().time
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val cal = Calendar.getInstance()

        try {
            cal.setTime(curDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val endDate = sdf.format(cal.time).toString()

        cal.add(Calendar.YEAR, -1)
        val startDate = sdf.format(cal.time).toString()

        return arrayListOf(startDate, endDate)
    }
    fun getComingTwoYears(): ArrayList<String> {
        val curDate = Calendar.getInstance().time
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val cal = Calendar.getInstance()

        try {
            cal.setTime(curDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val startDate = sdf.format(cal.time).toString()

        cal.add(Calendar.YEAR, 2)
        val endDate = sdf.format(cal.time).toString()

        return arrayListOf(startDate, endDate)
    }
}
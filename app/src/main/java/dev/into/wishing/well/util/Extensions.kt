package dev.into.wishing.well.util

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.util.TypedValue

import java.text.NumberFormat
import java.util.*

fun String.stripHtml(): String {
    val html: String = this
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {

        Html.fromHtml(html).toString()
    }
}

fun String.parsePrice():Float{
    val nf = NumberFormat.getInstance(Locale("tr","TR"))
    return nf.parse(this.replace("₺","").replace("TL","").stripHtml().trim())?.toFloat() ?: 1.0f

}

fun Float.toPixel(context: Context): Int {
    val metrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, metrics).toInt()
}
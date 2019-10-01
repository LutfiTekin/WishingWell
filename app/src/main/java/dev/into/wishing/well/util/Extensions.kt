package dev.into.wishing.well.util

import android.text.Html
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
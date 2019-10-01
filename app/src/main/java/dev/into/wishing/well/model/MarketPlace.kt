package dev.into.wishing.well.model

import android.text.Html
import android.webkit.JavascriptInterface
import androidx.annotation.WorkerThread
import dev.into.wishing.well.util.parsePrice
import dev.into.wishing.well.util.stripHtml
import org.jsoup.Jsoup
import java.text.NumberFormat
import java.util.*

enum class MarketPlace {
    GITTIGIDIYOR {
        override fun parseData(html: String): Product{
            val doc = Jsoup.parse(html)
            var price = doc.select("#sp-price-lowPrice").text()
            if (price.isNullOrEmpty()){
                price = doc.select("#sp-price-highPrice").text()
            }
            val title = doc.select(".h1-container > h1").text()
            val image = doc.select(".visible-m").attr("swapimg")
            return Product(title,price.parsePrice(),image)
        }
    },HEPSIBURADA {
        override fun parseData(html: String): Product{
            val doc = Jsoup.parse(html)
            var price = doc.select(".extra-discount-price > span").text().orEmpty()
            if (price.isEmpty()){
                price = doc.select("#offering-price").text()
            }
            val title = doc.select(".product-name").eachText()[0]
            val image = doc.select(".product-image").attr("src")
            return Product(title,price.parsePrice(),image)
        }
    },N11 {
        override fun parseData(html: String): Product{
            val doc = Jsoup.parse(html)
            val price = doc.select(".price").text()
            print("CRAWLED price $price")
            val title = doc.select(".title").eachText()[0]
            val image = doc.select(".swipe-wrap > figure").attr("data-org")
            return Product(title,price.parsePrice(),image)
        }
    },TRENDYOL {
        override fun parseData(html: String): Product{
            val doc = Jsoup.parse(html)
            var price = doc.select(".price__wrapper__percentage__sales_price > span").text()
            if (price.isEmpty()){
                price = doc.select(".price__container__sales_price > span").text()
            }
            val title = doc.select(".product_info__product_name").html()
            val image = doc.select(".gallery__image").attr("src")
            return Product(title,price.parsePrice(),image)
        }
    },AKAKCE {
        override fun parseData(html: String): Product{
            val doc = Jsoup.parse(html)
            val image = "https:" + doc.select(".pdi_v8 > a").attr("href")
            val price = doc.select(".pt_v8").eachText()[0]
            val title = doc.select(".pdt_v8 > h1").text()
            return Product(title,price.parsePrice(),image)
        }
    },AMAZONCOMTR {
        override fun parseData(html: String): Product {
            val doc = Jsoup.parse(html)
            val price = doc.select("#priceblock_ourprice").html()
            val title = doc.select("#main-image").attr("alt")
            val image = doc.select("#main-image").attr("src")
            println("CRAWL $title")
            return Product(title,price.parsePrice(),image)
        }
    };
    @WorkerThread
    abstract fun parseData(html: String): Product
}
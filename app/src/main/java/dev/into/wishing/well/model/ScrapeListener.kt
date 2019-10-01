package dev.into.wishing.well.model

interface ScrapeListener {
    fun productDataCrawled(productData: Product)
}
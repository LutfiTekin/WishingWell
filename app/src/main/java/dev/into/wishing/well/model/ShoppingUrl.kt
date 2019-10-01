package dev.into.wishing.well.model

data class ShoppingUrl(val url: String){

    val marketPlace: MarketPlace?
        get() {
            return when {
                url.contains("hepsiburada.com") -> MarketPlace.HEPSIBURADA
                url.contains("n11.com") -> MarketPlace.N11
                url.contains("akakce.com") -> MarketPlace.AKAKCE
                url.contains("trendyol.com") -> MarketPlace.TRENDYOL
                url.contains("gittigidiyor.com") -> MarketPlace.GITTIGIDIYOR
                url.contains("www.amazon.com.tr") -> MarketPlace.AMAZONCOMTR
                else -> null
            }
        }

}
package dev.into.wishing.well.model


import com.google.gson.Gson


class Product(var url: String = ""){

    var name: String? = null
    var price: Float? = null
    var imageUrl: String? = null

    constructor(name: String? ,price: Float?,imageUrl: String?, url: String = "") : this(){
        this.name = name
        this.price = price
        this.imageUrl = imageUrl
        this.url = url
    }


    val data: String
        get() {
            return Gson().toJson(this)
        }

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

    override fun toString(): String {
        return "Product Information \n Name: $name, \n Price $price, \n Image $imageUrl"
    }

    companion object{
        fun productFrom(data: String): Product{
            return Gson().fromJson<Product>(data, Product::class.java)
        }
    }
}
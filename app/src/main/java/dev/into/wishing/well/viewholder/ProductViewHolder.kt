package dev.into.wishing.well.viewholder

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.into.wishing.well.R
import dev.into.wishing.well.db.ProductEntity
import dev.into.wishing.well.model.IntoDevScraper
import dev.into.wishing.well.model.Product

class ProductViewHolder internal constructor(inflater: LayoutInflater, parent: ViewGroup, @LayoutRes res: Int)
    : RecyclerView.ViewHolder(inflater.inflate(res, parent, false)){
    private val card: CardView? = itemView.findViewById(R.id.card)
    private val title: TextView? = itemView.findViewById(R.id.productTitle)
    private val image: ImageView? = itemView.findViewById(R.id.productImage)
    private val price: TextView? = itemView.findViewById(R.id.productPrice)
    private val headerView: TextView? = itemView.findViewById(R.id.headerText)
    private val browser: WebView? = itemView.findViewById(R.id.browser)

    var product: Product? = null

    init {
        card?.setOnClickListener {
            product?.let {p ->
                card.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(p.url)))
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun bind(product: Product){
        this.product = product
        title?.text = product.name
        price?.text = "${product.price}₺"
        image ?: return
        Glide.with(image).load(product.imageUrl).into(image)
        browser ?: return
        IntoDevScraper(browser, product).refreshProduct {
            price?.text = "${it.price}₺"
        }
    }

    private fun bind(header: String){
        headerView?.text = header
    }

    fun bind(data: Any){
        when (data) {
            is Product -> bind(data)
            is String -> bind(data)
            is ProductEntity -> bind(data.product)
        }
    }

}
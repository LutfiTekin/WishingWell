package dev.into.wishing.well.viewholder

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.into.wishing.well.R
import dev.into.wishing.well.model.Product

class ProductViewHolder internal constructor(inflater: LayoutInflater, parent: ViewGroup, @LayoutRes res: Int)
    : RecyclerView.ViewHolder(inflater.inflate(res, parent, false)){
    val card: CardView = itemView.findViewById(R.id.card)
    val title: TextView = itemView.findViewById(R.id.productTitle)
    val image: ImageView = itemView.findViewById(R.id.productImage)
    val price: TextView = itemView.findViewById(R.id.productPrice)

    var product: Product? = null

    init {
        card.setOnClickListener {
            product?.let {p ->
                card.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(p.url)))
            }
        }

    }

    @SuppressLint("SetTextI18n")
    fun bind(product: Product){
        this.product = product
        title.text = product.name
        price.text = "${product.price}₺"
        Glide.with(image).load(product.imageUrl).into(image)
    }

}
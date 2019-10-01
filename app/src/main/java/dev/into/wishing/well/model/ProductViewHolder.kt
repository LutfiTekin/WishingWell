package dev.into.wishing.well.model

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
import kotlinx.android.synthetic.main.product_card.view.*

class ProductViewHolder internal constructor(inflater: LayoutInflater, parent: ViewGroup, @LayoutRes res: Int)
    : RecyclerView.ViewHolder(inflater.inflate(res, parent, false)){
    val card: CardView = itemView.findViewById(R.id.card)
    val title: TextView = itemView.findViewById(R.id.productTitle)
    val image: ImageView = itemView.findViewById(R.id.prodcutImage)
    val price: TextView = itemView.findViewById(R.id.productPrice)

    var product: Product? = null

    init {
        card.setOnClickListener {
            product?.let {p ->
                card.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(p.url)))
            }
        }

    }

    fun bind(product: Product){
        this.product = product
        title.text = product.name
        price.text = "${product.price}₺"
        Glide.with(image).load(product.imageUrl).into(image)
    }

}
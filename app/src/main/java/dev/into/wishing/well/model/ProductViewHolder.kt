package dev.into.wishing.well.model

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.into.wishing.well.R
import kotlinx.android.synthetic.main.product_card.view.*

class ProductViewHolder internal constructor(inflater: LayoutInflater, parent: ViewGroup, @LayoutRes res: Int)
    : RecyclerView.ViewHolder(inflater.inflate(res, parent, false)){

    val title: TextView = itemView.findViewById(R.id.productTitle)
    val link: TextView = itemView.findViewById(R.id.productLink)
    val image: ImageView = itemView.findViewById(R.id.prodcutImage)
    val price: TextView = itemView.findViewById(R.id.productPrice)

    fun bind(product: Product){
        title.text = product.name
        link.text = product.url
        price.text = "${product.price}₺"
        Glide.with(image).load(product.imageUrl).into(image)
    }

}
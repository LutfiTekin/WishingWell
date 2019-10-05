package dev.into.wishing.well.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.into.wishing.well.R
import dev.into.wishing.well.db.ProductEntity
import dev.into.wishing.well.viewholder.ProductViewHolder

class ProductAdapter(val items: List<ProductEntity>) : RecyclerView.Adapter<ProductViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        return ProductViewHolder(
            inflater,
            parent,
            R.layout.product_card
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(items[position].product)
    }


}
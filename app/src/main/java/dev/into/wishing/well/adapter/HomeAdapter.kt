package dev.into.wishing.well.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.into.wishing.well.R
import dev.into.wishing.well.db.ProductEntity
import dev.into.wishing.well.model.Product
import dev.into.wishing.well.viewholder.ProductViewHolder

const val LIST_ITEM_HEADER = 0
const val LIST_ITEM_PRODUCT = 1

class HomeAdapter(val items: List<Any>) : RecyclerView.Adapter<ProductViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val res = when(viewType){
            LIST_ITEM_HEADER -> R.layout.list_header
            LIST_ITEM_PRODUCT -> R.layout.product_card
            else -> R.layout.product_card
        }
        return ProductViewHolder(
            inflater,
            parent,
            res
        )
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is String -> LIST_ITEM_HEADER
            is Product -> LIST_ITEM_PRODUCT
            is ProductEntity -> LIST_ITEM_PRODUCT
            else -> super.getItemViewType(position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(items[position])
    }



}
package dev.into.wishing.well.ui.fragments.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dev.into.wishing.well.R
import dev.into.wishing.well.model.ProductAdapter

import dev.into.wishing.well.model.ProductViewModel
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment() {

    lateinit var productViewHolder: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_history, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productViewHolder = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        historyList.layoutManager = LinearLayoutManager(this.context)

        productViewHolder.fetch().observe(this, Observer {
            Log.d("HiFr",it.size.toString())
            historyList.adapter = ProductAdapter(it.reversed())
            var sum = 0.toFloat()
            it.forEach {
                sum += (it.product.price ?: 0.toFloat())
            }
            Toast.makeText(context,"Total Price $sum₺",Toast.LENGTH_LONG).show()
        })

        super.onViewCreated(view, savedInstanceState)
    }
}
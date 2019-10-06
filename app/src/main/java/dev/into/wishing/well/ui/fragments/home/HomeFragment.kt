package dev.into.wishing.well.ui.fragments.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dev.into.wishing.well.R
import dev.into.wishing.well.adapter.HomeAdapter
import dev.into.wishing.well.db.CollectionsDB
import dev.into.wishing.well.db.ProductEntity
import dev.into.wishing.well.model.ProductViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    lateinit var productViewHolder: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productViewHolder = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    lateinit var mContext: Context

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        homeList.layoutManager = LinearLayoutManager(mContext)
        productViewHolder.fetch().observe(this, Observer {
            emptyView.isVisible = it.isEmpty()
            it.createListWithHeaders()
        })
        super.onViewCreated(view, savedInstanceState)
    }

    private fun List<ProductEntity>.createListWithHeaders() {
        Log.d("HomeFr","Sİze ${this.size}  $this")
        val mutableList = mutableListOf<Any>()
        //Observe collections data
        CollectionsDB.db(mContext).data().fetchCollections().observe(this@HomeFragment, Observer { collections ->
            collections.forEach { collection ->
                //Summarized price of that list
                val sum = this.filter { it.collection == collection }.sumBy { it.product.price?.toInt() ?: 0 }
                //Add header
                mutableList.add(collection + " - " + sum + "₺")
                //Then corresponding items
                mutableList.addAll(this.filter { it.collection == collection })
            }
            Log.d("HomeFr","Sİze ${mutableList.size}  $mutableList")
            homeList.adapter = HomeAdapter(mutableList.toList())
        })
    }

}
package dev.into.wishing.well.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dev.into.wishing.well.R
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productViewHolder.fetch().observe(this, Observer {
            emptyView.isVisible = it.isEmpty()
        })
        super.onViewCreated(view, savedInstanceState)
    }
}
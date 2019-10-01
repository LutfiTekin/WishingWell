package dev.into.wishing.well.ui.fragments.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dev.into.wishing.well.R
import dev.into.wishing.well.model.ProductViewModel


class AddProductFragment : Fragment() {


    private lateinit var productViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        productViewModel =
            ViewModelProviders.of(this).get(ProductViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        var list = "Loaded list"
        productViewModel.fetch().observe(this, Observer {
            it.forEach {
                list = "$list ${it.product.name}"
            }

        })

        return root
    }
}
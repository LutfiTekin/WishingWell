package dev.into.wishing.well.ui.fragments.dashboard

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import dev.into.wishing.well.R
import dev.into.wishing.well.model.IntoDevScraper
import dev.into.wishing.well.model.Product
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.browser


class AddProductFragment : Fragment() {


    //private lateinit var productViewModel: ProductViewModel
    private lateinit var scraper: IntoDevScraper
    var url: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("args","$arguments")
        arguments?.let {
            url = it.getString("url","")
        }
        //productViewModel =
          //  ViewModelProviders.of(this).get(ProductViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        /*var list = "Loaded list"
        productViewModel.fetch().observe(this, Observer {
            it.forEach {
                list = "$list ${it.product.name}"
            }

        })*/

        return root
    }

    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    @UiThread
    private fun Product.updateUI() {
        Log.d("updateUI","$this")
        productTitle.setText(name,TextView.BufferType.EDITABLE)
        autoCompletedImage.setImageBitmap(null)
        autoCompletedImage.visibility = View.VISIBLE
        Glide.with(fragmentView).load(imageUrl).into(autoCompletedImage)
        productPrice.setText("${price}₺",TextView.BufferType.EDITABLE)
        progressBar.visibility = View.INVISIBLE
    }

    private lateinit var fragmentView: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentView = view
        fill.setOnClickListener {
            url = productUrl.text.toString()
            progressBar.visibility = View.VISIBLE
            IntoDevScraper(browser,Product(url)).refreshProduct {
                productUrl.post { it.updateUI() }
            }
        }

        if (url.isNotEmpty()){
            productUrl.setText(url,TextView.BufferType.EDITABLE)
            IntoDevScraper(browser,Product(url)).refreshProduct {
                productUrl.post { it.updateUI() }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}
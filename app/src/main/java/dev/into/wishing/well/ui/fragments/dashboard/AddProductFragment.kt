package dev.into.wishing.well.ui.fragments.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import dev.into.wishing.well.R
import dev.into.wishing.well.db.CollectionsDB
import dev.into.wishing.well.db.ProductEntity
import dev.into.wishing.well.model.IntoDevScraper
import dev.into.wishing.well.model.Product
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.android.synthetic.main.fragment_add_product.browser


class AddProductFragment : Fragment() {


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

        /*var list = "Loaded list"
        productViewModel.fetch().observe(this, Observer {
            it.forEach {
                list = "$list ${it.product.name}"
            }

        })*/

        return inflater.inflate(R.layout.fragment_add_product, container, false)
    }

    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    @UiThread
    private fun Product.updateUI() {
        product = this
        addProduct.isEnabled = true
        Log.d("updateUI","$this")
        productTitle.setText(name,TextView.BufferType.EDITABLE)
        autoCompletedImage.setImageBitmap(null)
        autoCompletedImage.visibility = View.VISIBLE
        Glide.with(fragmentView).load(imageUrl).into(autoCompletedImage)
        productPrice.setText("${price}₺",TextView.BufferType.EDITABLE)
        progressBar.visibility = View.INVISIBLE
    }

    private lateinit var fragmentView: View
    private lateinit var product: Product

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentView = view
        addProduct.isEnabled = false
        fill.setOnClickListener {
            url = productUrl.text.toString()
            progressBar.visibility = View.VISIBLE
            IntoDevScraper(browser,Product(url)).refreshProduct {
                productUrl.post { it.updateUI() }
            }
        }

        addProduct.setOnClickListener {
            if (productPrice.text.isNotEmpty() and productTitle.text.isNotEmpty()){
                Thread{
                    CollectionsDB.db(mContext).data().insert(ProductEntity(product.data,"wishlist",false))
                }.start()
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
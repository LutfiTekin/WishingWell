package dev.into.wishing.well.ui.fragments.create

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.UiThread
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import dev.into.wishing.well.R
import dev.into.wishing.well.db.CollectionsDB
import dev.into.wishing.well.db.ProductEntity
import dev.into.wishing.well.model.IntoDevScraper
import dev.into.wishing.well.model.Product
import dev.into.wishing.well.model.ProductViewModel
import dev.into.wishing.well.util.toPixel
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.android.synthetic.main.fragment_add_product.browser
import kotlinx.android.synthetic.main.fragment_add_product.productPrice
import kotlinx.android.synthetic.main.fragment_add_product.productTitle


class AddProductFragment : Fragment() {


    var url: String = ""
    var selectedList = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("args","$arguments")
        arguments?.let {
            url = it.getString("url","")
        }


        return inflater.inflate(R.layout.fragment_add_product, container, false)
    }

    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }
    @SuppressLint("SetTextI18n")
    @UiThread
    private fun Product.updateUI() {
        product = this
        productTitle.setText(name,TextView.BufferType.EDITABLE)
        autoCompletedImage.setImageBitmap(null)
        autoCompletedImage.visibility = View.VISIBLE
        Glide.with(fragmentView).load(imageUrl).into(autoCompletedImage)
        productPrice.setText("${price}₺",TextView.BufferType.EDITABLE)
        progressBar.visibility = View.INVISIBLE
        fill.isChecked = false
        validateForm()
    }


    private fun Spinner.setup(new: String = ""){
        val arrayList = arrayListOf<String>()
        if (new.isNotEmpty())
            arrayList.add(new)
        arrayList.add(mContext.getString(R.string.spinner_select_placeholder))
        val collections = CollectionsDB.db(context).data().fetchCollections().observe(this@AddProductFragment,
            Observer {collections ->
                collections.forEach {
                    arrayList.add(it)
                }
                val adapter = ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,arrayList)
                this.adapter = adapter
            })

        onItemSelectedListener = spinnerItemSelectedListener
    }

    val spinnerItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(p0: AdapterView<*>?) {

        }

        override fun onItemSelected(adapter: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            selectedList = adapter?.selectedItem as String? ?: return
            validateForm()
        }

    }

    private fun showAddNewListAlert(){
        val cfET = EditText(mContext)
        cfET.hint = getString(R.string.new_list_alert_hint)
        cfET.setTextColor(ContextCompat.getColor(mContext, R.color.textColorPrimary))
        val spacing = 16f.toPixel(mContext)
        AlertDialog.Builder(ContextThemeWrapper(mContext, R.style.compactAlertDialogTheme))
            .setTitle(getString(R.string.new_list_alert_title))
            .setMessage(getString(R.string.new_list_alert_message))
            .setView(cfET, spacing, 0, spacing, 0)
            .setPositiveButton(getString(R.string.add)) { dialog, which ->
                listSpinner.setup(cfET.text.toString())
            }.setNegativeButton(R.string.cancel) { dialogInterface, i -> dialogInterface.cancel() }
            .setIcon(R.drawable.ic_dashboard_black_24dp)
            .setCancelable(false)
            .show()
    }

    private lateinit var fragmentView: View
    private lateinit var product: Product

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentView = view
        addProduct.isEnabled = false
        listSpinner.setup()
        addNewList.setOnClickListener {
            showAddNewListAlert()
        }
        fill.setOnCheckedChangeListener{ compoundButton: CompoundButton, isChecked: Boolean ->
            if (!compoundButton.isPressed)
                return@setOnCheckedChangeListener
            if (isChecked){
                url = productUrl.text.toString()
                progressBar.visibility = View.VISIBLE
                IntoDevScraper(browser,Product(url)).refreshProduct {
                    productUrl.post { it.updateUI() }
                }
            }else{
                productPrice.setText("",TextView.BufferType.EDITABLE)
                productTitle.setText("",TextView.BufferType.EDITABLE)
                autoCompletedImage.setImageBitmap(null)
                addProduct.isEnabled = false
                progressBar.visibility = View.GONE
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

    fun validateForm(){
        //Default state
        addProduct.isEnabled = false
        /**
         * Return if necessary fields are empty
         */

        if (productTitle.text.isEmpty())
            return
        if (productPrice.text.isEmpty())
            return
        if (selectedList.isEmpty()
            && selectedList == mContext.getString(R.string.spinner_select_placeholder))
            return
        addProduct.isEnabled = true
    }


}
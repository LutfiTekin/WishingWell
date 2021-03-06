package dev.into.wishing.well.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import org.jsoup.Jsoup


import android.webkit.WebSettings
import android.webkit.WebView
import android.view.View
import android.webkit.WebChromeClient

import android.webkit.WebViewClient
import com.bumptech.glide.Glide
import dev.into.wishing.well.R
import dev.into.wishing.well.db.ProductEntity
import dev.into.wishing.well.model.*
import dev.into.wishing.well.util.stripHtml
import kotlinx.android.synthetic.main.activity_main.*


class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this,WishlistCollectionActivity::class.java))


    }


}



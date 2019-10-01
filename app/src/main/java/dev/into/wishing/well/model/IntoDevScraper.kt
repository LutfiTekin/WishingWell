package dev.into.wishing.well.model

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class IntoDevScraper(val browser: WebView, val product: Product) {

    var scrapeListener: ScrapeListener? = null

    init {
        browser.configForCrawl()
    }

    private fun loadProduct(){
        browser.addJavascriptInterface(JSInterface(), "JSBridge")
        browser.loadUrl(product.url)
        //browser.loadUrl(browser.url)
    }

    fun stop(){
        browser.post {
            browser.stopLoading()
        }
    }

    fun refreshProduct(loadedProduct : (Product) -> Unit){
        scrapeListener = object : ScrapeListener{
            override fun productDataCrawled(productData: Product) {
                productData.url = product.url
                loadedProduct(productData)
                scrapeListener = null
                stop()
            }

        }
        loadProduct()
    }

    private inner class JSInterface() {
        @android.webkit.JavascriptInterface
        fun showHTML(html: String) {
            Thread {
                try {
                    val product = product.marketPlace?.parseData(html) ?: throw Exception()
                    scrapeListener?.productDataCrawled(product)
                    browser.post {
                        browser.stopLoading()
                    }
                } catch (e: Exception) {
                    println("CRAWLER error $e ${e.message}")
                }
            }.start()
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun WebView.configForCrawl() {
        visibility = View.INVISIBLE
        setLayerType(View.LAYER_TYPE_NONE, null)
        settings.setUserAgentString("Mozilla/5.0 (Linux; U; Android <android>; <locale>; <device> Build/<build>) AppleWebKit/<webkit> (KHTML, like Gecko) Version/4.0 Mobile Safari/<safari>");
        settings.javaScriptEnabled = true
        settings.blockNetworkImage = true
        settings.domStorageEnabled = false
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.loadsImagesAutomatically = false
        settings.setGeolocationEnabled(true)
        settings.setSupportZoom(false)
        webChromeClient = object : WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress in 40..100){
                    loadUrl("javascript:window.JSBridge.showHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');")
                }
                super.onProgressChanged(view, newProgress)
            }
        }
        webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                loadUrl("javascript:window.JSBridge.showHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');")
                super.onPageFinished(view, url)
            }
        }
    }

}
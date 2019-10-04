package dev.into.wishing.well.util

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.into.wishing.well.R
import dev.into.wishing.well.ui.LandingActivity
import dev.into.wishing.well.ui.WishlistCollectionActivity


class ShareTargetActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_target)
        val sharedText = intent.extras?.getString(Intent.EXTRA_TEXT).orEmpty()

        val intent = Intent(this, WishlistCollectionActivity::class.java)
        intent.putExtra("url","http" +sharedText.substringAfter("http"))
        startActivity(intent)
    }


}

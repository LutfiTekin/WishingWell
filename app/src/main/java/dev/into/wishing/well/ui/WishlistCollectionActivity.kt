package dev.into.wishing.well.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dev.into.wishing.well.R
import dev.into.wishing.well.model.ProductViewModel

class WishlistCollectionActivity : AppCompatActivity() {

    lateinit var productViewModel: ProductViewModel
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist_collection)
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)


        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_create, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->

        }
        handleIntent()

    }

    private fun handleIntent() {
        intent.extras?.getString("url")?.let { url ->
            val bundle = bundleOf("url" to url)
            navController.navigate(R.id.navigation_create, bundle)
        }
    }

}

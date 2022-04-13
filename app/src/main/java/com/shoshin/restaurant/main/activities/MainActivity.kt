package com.shoshin.restaurant.main.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nex3z.notificationbadge.NotificationBadge
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.ActivityMainBinding
import com.shoshin.restaurant.ui.fragments.cart.CartViewModel1
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::bind)
    private val navController: NavController by lazy { findNavController(R.id.fragmentContainer) }
    private val topLevelDestinations = setOf(
        R.id.profileScreen,
        R.id.menuScreen,
        R.id.cartScreen,
        R.id.ordersScreen,
        R.id.loginEnterPhone
    )
    private val appBarConfiguration: AppBarConfiguration by lazy{
        AppBarConfiguration(topLevelDestinations)
    }
    private var badge: NotificationBadge? = null
    private val cartViewModel: CartViewModel1 by viewModels()
    private var cartItemsCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)

        cartViewModel.cartItemsCount.observe(this, {
            Log.e("mainActCountChange", "$it")
            cartItemsCount = it
            invalidateOptionsMenu()
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        val view = menu?.findItem(R.id.cart_menu_item)?.actionView
        view?.setOnClickListener {
            navController.navigate(R.id.cart)
        }
        badge = view?.findViewById(R.id.badge)
        updateCartIcon()
        return true
    }

    private fun updateCartIcon() {
        badge?.let {
            if(cartItemsCount == 0) {
                badge?.visibility = View.INVISIBLE
            } else {
                badge?.visibility = View.VISIBLE
                badge?.setText("$cartItemsCount")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.e("onOptionsItemSelected", "onOptionsItemSelected")
        when(item.itemId) {
            R.id.cart_menu_item -> {
                navController.navigate(R.id.cart)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        updateCartIcon()
    }
}
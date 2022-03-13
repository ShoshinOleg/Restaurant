package com.shoshin.restaurant.main.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nex3z.notificationbadge.NotificationBadge
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.ActivityMainBinding
import com.shoshin.restaurant.ui.fragments.cart.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::bind)
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var badge: NotificationBadge? = null
    private var cartViewModel: CartViewModel? = null
    private var cartItemsCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        val topLevelDestinations = setOf(
            R.id.profile,
            R.id.menu,
            R.id.cart,
            R.id.loginEnterPhone
        )
        navController = findNavController(R.id.fragmentContainer)
        appBarConfiguration = AppBarConfiguration(topLevelDestinations)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.profile ->
                    if(Firebase.auth.currentUser == null) {
                        navController.navigate(R.id.loginEnterPhone)
                    } else {
                        navController.navigate(R.id.profile)
                    }
                    R.id.orders ->
                        if(Firebase.auth.currentUser == null) {
                            navController.navigate(R.id.loginEnterPhone)
                        } else {
                            navController.navigate(R.id.orders)
                        }
                else -> navController.navigate(item.itemId)
            }
            true
        }

        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        cartViewModel?.subscribeCartItemsCount()?.observe(this, {
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
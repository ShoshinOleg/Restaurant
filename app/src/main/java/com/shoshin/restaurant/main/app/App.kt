package com.shoshin.restaurant.main.app

import android.app.Application
import androidx.room.Room
import com.shoshin.restaurant.di.AppComponent
import com.shoshin.restaurant.di.ContextModule
import com.shoshin.restaurant.di.DaggerAppComponent
import com.shoshin.restaurant.local_db.AppDatabase

class App: Application() {
    lateinit var appComponent: AppComponent
        private set
    private lateinit var database: AppDatabase

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        initDependencies()
        App.instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
            .allowMainThreadQueries()
            .build()
    }

    private fun initDependencies() {
        appComponent = DaggerAppComponent.builder()
            .contextModule(ContextModule(applicationContext))
            .build()
    }

    fun getDatabase(): AppDatabase {
        return database
    }
}
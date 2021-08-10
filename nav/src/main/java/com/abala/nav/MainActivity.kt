package com.abala.nav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setNavHostFragment()
//        setNavHost()
    }

    //Method 1
//    override fun onSupportNavigateUp(): Boolean {
//        val host = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container)
//            ?: return false
//        return NavHostFragment.findNavController(host).navigateUp()
//    }

    //Method 2
    fun setNavHost() {
        val host = NavHostFragment.create(R.navigation.navigation_graph)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_container, host)
            .setPrimaryNavigationFragment(host)
            .commit();
    }

    //Method 3 需要绑定底部工具栏则使用此方法
    fun setNavHostFragment() {
        val navigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val host =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as? NavHostFragment
        val controller = host?.navController ?: return
        NavigationUI.setupWithNavController(navigationView, controller)
    }
}
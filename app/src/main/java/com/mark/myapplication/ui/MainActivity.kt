package com.mark.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import com.mark.myapplication.R
import com.mark.myapplication.databinding.ActivityMainBinding
import com.mark.myapplication.ui.fragment.PlayerHomeFragment
import java.io.File

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initLayout() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun initView() {
        setSupportActionBar(binding.homeToolbarLayout.homeToolbar)
        supportFragmentManager.beginTransaction()
            .add(R.id.activity_main_container, PlayerHomeFragment(), "playerHome")
            .commit()
        binding.homeNav.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                invalidateOptionsMenu()

                return true
            }
        })

        val actionBarDrawerToggle =
            ActionBarDrawerToggle(this, binding.homeDrawer, binding.homeToolbarLayout.homeToolbar, R.string.open, R.string.close)
        actionBarDrawerToggle.syncState()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun initData() {

    }

    companion object {
        init {
            System.loadLibrary("myapplication")
            System.loadLibrary("avcodec-59")
            System.loadLibrary("avfilter-8")
            System.loadLibrary("avformat-59")
            System.loadLibrary("avutil-57")
            System.loadLibrary("swresample-4")
            System.loadLibrary("swscale-6")
        }
    }
}
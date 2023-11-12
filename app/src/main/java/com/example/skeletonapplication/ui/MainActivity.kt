package com.example.skeletonapplication.ui

import androidx.fragment.app.Fragment
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.skeletonapplication.R
import com.example.skeletonapplication.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity


class MainActivity : DaggerAppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        addHomeFragment()
    }

    private fun addHomeFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment: Fragment = HomeFragment.newInstance()
        fragmentTransaction.add(R.id.container, fragment, "myFragmentTag")
        fragmentTransaction.commit()    }
}
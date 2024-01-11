package com.example.skeletonapplication.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.skeletonapplication.R
import com.example.skeletonapplication.databinding.FragmentSplashBinding
import com.example.skeletonapplication.ui.List.ListFragment
import dagger.android.support.DaggerFragment

class SplashFragment : DaggerFragment() {

    private lateinit var splashBinding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        splashBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        return splashBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
            navigateToListFragment()
        }, SPLASH_TIMER)
    }

    private fun navigateToListFragment() {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        val listFragment: Fragment = ListFragment.newInstance()
        fragmentTransaction?.replace(R.id.container, listFragment, "listFragment")
        fragmentTransaction?.commit()
    }

    companion object {

        const val SPLASH_TIMER = 3000L
        @JvmStatic
        fun newInstance() = SplashFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }
}
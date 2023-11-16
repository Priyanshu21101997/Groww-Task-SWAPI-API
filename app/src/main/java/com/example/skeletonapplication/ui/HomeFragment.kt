package com.example.skeletonapplication.ui

import android.content.Context
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnScrollChangeListener
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skeletonapplication.listeners.Onclick
import com.example.skeletonapplication.R
import com.example.skeletonapplication.databinding.FragmentHomeBinding
import com.example.skeletonapplication.models.Result
import com.example.skeletonapplication.viewmodel.HomeFragmentViewModel
import com.example.skeletonapplication.viewmodel.HomeFragmentViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class HomeFragment : DaggerFragment(), Onclick {

    @Inject
    lateinit var viewModelFactory: HomeFragmentViewModelFactory

    private var mainViewModel: HomeFragmentViewModel? = null

    private lateinit var homeBinding: FragmentHomeBinding

    private var currentItems = 0
    private var scrolledOutItems = 0
    private var totalItems = 0
    private var isScrolling = false
    private var pageNo = 0;
    private lateinit var adapter:CustomAdapter

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return homeBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mainViewModel = ViewModelProvider(this,viewModelFactory
        )[HomeFragmentViewModel::class.java]

        mainViewModel!!.takeDecisionForQuotes()

        homeBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        val data = ArrayList<Result>()
        adapter = CustomAdapter()
        adapter.setListener(this)
        homeBinding.recyclerView.adapter = adapter

        homeBinding.recyclerView.setOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrolling = true
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                 homeBinding.recyclerView.layoutManager?.apply {
                    currentItems = (this as LinearLayoutManager).childCount
                     totalItems = this.itemCount
                     scrolledOutItems = this.findFirstVisibleItemPosition()

                     if(isScrolling && currentItems + scrolledOutItems == totalItems ){
                         isScrolling = false
                         pageNo ++
                         mainViewModel!!.takeDecisionForQuotes(pageNo)
                     }
                }
            }
        })


        mainViewModel!!.quotesLiveData.observe(viewLifecycleOwner) {
            Log.d("RE_LIFE", "onViewCreated: ${it.size} ")
            adapter.submitList(it)
        }

        mainViewModel!!.progressLD.observe(viewLifecycleOwner) {
            if(it){
                homeBinding.progressCircular.visibility = View.VISIBLE
            }
            else{
                homeBinding.progressCircular.visibility = View.GONE
            }
        }

    }


    override fun onCommentClicked(model: Result) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        val fragment: Fragment = DetailFragment.newInstance(model.author, model.content)
        fragmentTransaction?.replace(R.id.container, fragment, "myFragmentTag")?.addToBackStack(null)

        fragmentTransaction?.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.setListener(null)
    }

}


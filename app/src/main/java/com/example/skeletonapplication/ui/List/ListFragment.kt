package com.example.skeletonapplication.ui.List

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skeletonapplication.R
import com.example.skeletonapplication.databinding.FragmentListBinding
import com.example.skeletonapplication.listeners.IOnCharacterClicked
import com.example.skeletonapplication.models.Results
import com.example.skeletonapplication.ui.detail.DetailFragment
import com.example.skeletonapplication.viewmodel.ListFragmentViewModel
import com.example.skeletonapplication.viewmodel.ListFragmentViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class ListFragment : DaggerFragment(), IOnCharacterClicked {

    @Inject
    lateinit var viewModelFactory: ListFragmentViewModelFactory

    private lateinit var listViewModel: ListFragmentViewModel

    private lateinit var listBinding: FragmentListBinding

    private var currentItems = 0
    private var scrolledOutItems = 0
    private var totalItems = 0
    private var isScrolling = false
    private var pageNo = 1;
    private lateinit var adapter: CharacterListAdapter

    companion object {

        const val GENERIC_ERROR = "SOME ERROR OCCURED"

        @JvmStatic
        fun newInstance() = ListFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        return listBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()

        listViewModel.handleCharactersList()

        listBinding.recyclerView.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrolling = true
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                listBinding.recyclerView.layoutManager?.apply {
                    currentItems = (this as LinearLayoutManager).childCount
                    totalItems = this.itemCount
                    scrolledOutItems = this.findFirstVisibleItemPosition()

                    if (isScrolling && currentItems + scrolledOutItems == totalItems) {
                        isScrolling = false
                        pageNo++
                        listViewModel.handleCharactersList(pageNo)
                    }
                }
            }
        })


        listViewModel.resultsLiveData.observe(viewLifecycleOwner) {
            adapter.setResults(it)
            if (it.isNotEmpty()) {
                listBinding.filter.visibility = View.VISIBLE
            }
        }

        listViewModel.progressLD.observe(viewLifecycleOwner) {
            if (it) {
                listBinding.progressCircular.visibility = View.VISIBLE
            } else {
                listBinding.progressCircular.visibility = View.GONE
            }
        }

        listViewModel.errorLD.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), GENERIC_ERROR, Toast.LENGTH_SHORT).show()
        }

        listBinding.filter.setOnClickListener {
            openBottomSheet()
        }

        listViewModel.filterLD.observe(viewLifecycleOwner) {
            adapter.displayListBasedOnFilter(it)
        }

    }

    private fun openBottomSheet() {
        val bottomSheet = BottomSheet()
        bottomSheet.show(activity?.supportFragmentManager!!, "BottomSheet")
    }


    override fun onCharacterClicked(model: Results) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        val fragment: Fragment = DetailFragment.newInstance(model)
        fragmentTransaction?.replace(R.id.container, fragment, "myFragmentTag")?.addToBackStack(null)
        fragmentTransaction?.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.setListener(null)
    }

    private fun setupViewModel() {
        listViewModel = ViewModelProvider(
            requireActivity(), viewModelFactory
        )[ListFragmentViewModel::class.java]
    }

    private fun setupRecyclerView() {
        listBinding.recyclerView.layoutManager = GridLayoutManager(activity, 2)
        adapter = CharacterListAdapter()
        adapter.setListener(this)
        listBinding.recyclerView.adapter = adapter
    }
}


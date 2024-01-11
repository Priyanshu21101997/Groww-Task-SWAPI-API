package com.example.skeletonapplication.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skeletonapplication.R
import com.example.skeletonapplication.databinding.FragmentDetailBinding
import com.example.skeletonapplication.models.Results
import com.example.skeletonapplication.ui.List.ListFragment
import com.example.skeletonapplication.utils.NetworkUtils
import com.example.skeletonapplication.viewmodel.DetailFragmentViewModel
import com.example.skeletonapplication.viewmodel.DetailFragmentViewModelFactory
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailFragment : DaggerFragment() {

    private var param1: ArrayList<String>? = null
    private lateinit var detailBinding: FragmentDetailBinding
    private lateinit var detailViewModel: DetailFragmentViewModel
    private lateinit var adapter: CharacterDetailAdapter
    private val compositeDisposable = CompositeDisposable()


    @Inject
    lateinit var viewModelFactory: DetailFragmentViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getStringArrayList(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return detailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
        detailViewModel.getFilmDetails(param1)

        detailViewModel.progressLD.observe(viewLifecycleOwner) {
            if (it) {
                detailBinding.progressCircular.visibility = View.VISIBLE
            } else {
                detailBinding.progressCircular.visibility = View.GONE
            }
        }

        detailViewModel.fimListSubject.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    adapter.setResults(it)
                }
            , {
                Toast.makeText(requireActivity(), ListFragment.GENERIC_ERROR, Toast.LENGTH_SHORT)
                    .show()
            }).also {
                compositeDisposable.add(it)
            }

        detailViewModel.errorSubject.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                showErrorWidget()
            }.also {
                compositeDisposable.add(it)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
    }

    private fun setupRecyclerView() {
        detailBinding.recyclerViewFilms.layoutManager = LinearLayoutManager(activity)
        adapter = CharacterDetailAdapter()
        detailBinding.recyclerViewFilms.adapter = adapter
    }

    private fun setupViewModel() {
        detailViewModel = ViewModelProvider(
            requireActivity(), viewModelFactory
        )[DetailFragmentViewModel::class.java]
    }

    private fun showErrorWidget() {
        detailBinding.apply {
            recyclerViewFilms.visibility = View.GONE
            errorWidget.visibility = View.VISIBLE
        }
    }

    private fun hideErrorWidget() {
        detailBinding.apply {
            recyclerViewFilms.visibility = View.VISIBLE
            errorWidget.visibility = View.GONE
        }
    }

    companion object {

        private const val ARG_PARAM1 = "param1"
        const val GENERIC_ERROR = "SOME ERROR OCCURED"

        @JvmStatic
        fun newInstance(param1: Results) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ARG_PARAM1, param1.films)
                }
            }
    }
}
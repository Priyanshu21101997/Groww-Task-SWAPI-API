package com.example.skeletonapplication.ui.List

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.skeletonapplication.R
import com.example.skeletonapplication.databinding.BottomSheetBinding
import com.example.skeletonapplication.utils.Filter
import com.example.skeletonapplication.viewmodel.ListFragmentViewModel
import com.example.skeletonapplication.viewmodel.ListFragmentViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class BottomSheet : BottomSheetDialogFragment(), HasAndroidInjector {

    private lateinit var bottomSheetBinding: BottomSheetBinding
    private lateinit var mainViewModel: ListFragmentViewModel

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    @Inject
    lateinit var viewModelFactory: ListFragmentViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bottomSheetBinding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet, container, false)
        return bottomSheetBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(
            requireActivity(), viewModelFactory
        )[ListFragmentViewModel::class.java]

        bottomSheetBinding.btnName.setOnClickListener {
            mainViewModel.updateFilter(Filter.NAME)
            dismiss()
        }

        bottomSheetBinding.btnCreation.setOnClickListener {
            mainViewModel.updateFilter(Filter.CREATION)
            dismiss()
        }

        bottomSheetBinding.btnUpdation.setOnClickListener {
            mainViewModel.updateFilter(Filter.UPDATION)
            dismiss()
        }

        bottomSheetBinding.radioMale.setOnClickListener {
            mainViewModel.updateFilter(Filter.MALES)
            dismiss()
        }

        bottomSheetBinding.radioFemale.setOnClickListener {
            mainViewModel.updateFilter(Filter.FEMALES)
            dismiss()
        }
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}
package com.example.skeletonapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skeletonapplication.models.Result
import com.example.skeletonapplication.repository.Repository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeFragmentViewModel(private val repository: Repository) : ViewModel() {

    private val quoteMutableLiveData: MutableLiveData<List<Result>> = MutableLiveData()
    val quotesLiveData: LiveData<List<Result>> = quoteMutableLiveData

    private val progressMLD: MutableLiveData<Boolean> = MutableLiveData()
    val progressLD: LiveData<Boolean> = progressMLD

    private val compositeDisposable = CompositeDisposable()


    fun takeDecisionForQuotes(pageNo : Int = 0) {
        progressMLD.postValue(true)
        repository.getQuotesFromDB(pageNo).subscribeOn(Schedulers.io())
            .subscribe({
                       if(it.isEmpty()) {
                           Log.d("RE_LIFE", "takeDecisionForQuotes: empty")
                            getQuotes(pageNo).subscribe({
                                showQuotes(pageNo)
                            },{
                            }).also { it1-> compositeDisposable.add(it1) }
                       }else{
                           showQuotes(pageNo)
                       }
            },{}).also { compositeDisposable.add(it) }
    }

    private fun showQuotes(pageNo: Int) {
            repository.getQuotesFromDB(pageNo).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    quoteMutableLiveData.postValue(it)
                    progressMLD.postValue(false)
                }, {
                    progressMLD.postValue(false)
                }).also {
                    compositeDisposable.add(it)
                }

    }

    private fun getQuotes(pageNo: Int): Single<Boolean> {
       return Single.create{emitter->
            repository.getQuotesFromApi(pageNo)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    repository.storeQuotesInDB(it.results).subscribe {
                        emitter.onSuccess(true)
                    }.also { it1-> compositeDisposable.add(it1) }
                }, {
                    emitter.onError(it)
                }).also {
                    compositeDisposable.add(it)
                }
        }
        }


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
package com.example.skeletonapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skeletonapplication.models.Results
import com.example.skeletonapplication.repository.Repository
import com.example.skeletonapplication.utils.Filter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListFragmentViewModel(private val repository: Repository) : ViewModel() {

    private val resultsMutableLiveData: MutableLiveData<List<Results>> = MutableLiveData()
    val resultsLiveData: LiveData<List<Results>> = resultsMutableLiveData

    private val progressMLD: MutableLiveData<Boolean> = MutableLiveData()
    val progressLD: LiveData<Boolean> = progressMLD

    private val errorMLD: MutableLiveData<Boolean> = MutableLiveData()
    val errorLD: LiveData<Boolean> = errorMLD

    private val compositeDisposable = CompositeDisposable()

    private val filterMLD: MutableLiveData<Filter> = MutableLiveData(Filter.NONE)
    val filterLD: LiveData<Filter> = filterMLD


    fun handleCharactersList(pageNo: Int = 1) {
        progressMLD.postValue(true)
        repository.getCharactersFromDB(pageNo).subscribeOn(Schedulers.io())
            .subscribe({
                if (it.isEmpty()) {
                    getCharactersFromApiAndStoreInDB(pageNo).subscribe({
                        showCharacters(pageNo)
                    }, {
                        progressMLD.postValue(false)
                        errorMLD.postValue(true)
                    }).also { it1 -> compositeDisposable.add(it1) }
                } else {
                    showCharacters(pageNo)
                }
            }, {
                progressMLD.postValue(false)
                errorMLD.postValue(true)
            }).also { compositeDisposable.add(it) }
    }

    private fun showCharacters(pageNo: Int) {
        repository.getCharactersFromDB(pageNo - 1).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                resultsMutableLiveData.postValue(it)
                progressMLD.postValue(false)
            }, {
                progressMLD.postValue(false)
            }).also {
                compositeDisposable.add(it)
            }

    }

    private fun getCharactersFromApiAndStoreInDB(pageNo: Int): Single<Boolean> {
        return Single.create { emitter ->
            repository.getCharactersFromApi(pageNo)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    repository.storeCharactersInDB(it.results).subscribe {
                        emitter.onSuccess(true)
                    }.also { it1 -> compositeDisposable.add(it1) }
                }, {
                    emitter.onError(it)
                }).also {
                    compositeDisposable.add(it)
                }
        }
    }

    fun updateFilter(filter: Filter) {
        filterMLD.postValue(filter)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
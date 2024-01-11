package com.example.skeletonapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import com.example.skeletonapplication.models.Films
import com.example.skeletonapplication.repository.Repository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class DetailFragmentViewModel(private val repository: Repository) : ViewModel() {

    private val progressMLD: MutableLiveData<Boolean> = MutableLiveData()
    val progressLD: LiveData<Boolean> = progressMLD
    private val filmList = ArrayList<Films>()
    val fimListSubject = PublishSubject.create<ArrayList<Films>>()
    val errorSubject = PublishSubject.create<Boolean>()

    private val compositeDisposable = CompositeDisposable()


    fun getFilmDetails(param1: ArrayList<String>?) {
        progressMLD.postValue(true)
        Observable.fromIterable(param1)
            .flatMap {
                repository.getFilmsFromApi(it).toObservable()
            }
            .doOnSubscribe {
                filmList.clear()
            }
            .doOnComplete {
                fimListSubject.onNext(filmList)
                progressMLD.postValue(false)
            }
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    filmList.add(it)
                },{
                    progressMLD.postValue(false)
                    errorSubject.onNext(true)
                }).also {
                compositeDisposable.add(it)
            }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
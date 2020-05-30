package com.kat.weather.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kat.weather.interfaces.OnWeatherDataImpl
import com.kat.weather.model.Location
import com.kat.weather.model.ResponseData
import com.kat.weather.network.ApiCalls
import com.kat.weather.viewmodel.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    private var mRepository: ApiCalls = ApiCalls()
    private lateinit var observableFirst: Observable<ResponseData>
    private lateinit var observableSecond: Observable<ResponseData>
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    var listOfData = MutableLiveData<MutableList<ResponseData>>()
    val locationCollection = mutableListOf<Location>()
    var isDataAvailabile: Boolean? = false

    /**
     * Method to get the weather data using lat lng
     *
     * @param lat represent lattitude
     * @param lng represent longitude
     * @return Observable of ResponseData type
     */
     fun getWeatherResponse(lat: String, lng: String): Observable<ResponseData> {
        return mRepository.fetchWeather(lat, lng)
            .map { response -> response }
    }

    /**
     * Method to concat two observable
     *
     * @param onWeatherDataImpl interface type
     */
    fun zipResponse(onWeatherDataImpl: OnWeatherDataImpl) {
        observableFirst =
            getWeatherResponse(
                locationCollection[Constants.ZERO].latitude,
                locationCollection[Constants.ZERO].longitude
            )
        observableSecond =
            getWeatherResponse(
                locationCollection[Constants.ONE].latitude,
                locationCollection[Constants.ONE].longitude
            )
        compositeDisposable.add(
            Observable.concat(observableFirst, observableSecond)
                .map { it }
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    println(it.message)
                }
                .subscribeBy({

                    onWeatherDataImpl.onFailure(it.message.toString())
                    println(it.message)
                    isDataAvailabile = false
                }, {
                    onWeatherDataImpl.onSuccess(it)
                    listOfData.postValue(it)
                    isDataAvailabile = true

                })
        )
    }

    /**
     * Method to dispose all stream
     */
    fun disposeAll() {
        compositeDisposable.dispose()
    }
}

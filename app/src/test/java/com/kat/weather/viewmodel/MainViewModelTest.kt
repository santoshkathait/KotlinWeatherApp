package com.kat.weather.viewmodel

import com.kat.weather.interfaces.OnWeatherDataImpl
import com.kat.weather.model.Currently
import com.kat.weather.model.Location
import com.kat.weather.model.ResponseData
import com.kat.weather.network.ApiCalls
import com.kat.weather.ui.main.MainViewModel
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel

    @Mock
    lateinit var onWeatherDataImpl: OnWeatherDataImpl

    @Mock
    lateinit var observableFirst: Observable<ResponseData>

    @Mock
    private lateinit var list: MutableList<ResponseData>

    @Mock
    private lateinit var responseData: ResponseData

    @Mock
    private lateinit var currently: Currently

//    @Mock
//    private lateinit var mRepository: ApiCalls

    @Before
    fun setUpWeatherVM() {
        MockitoAnnotations.initMocks(this)
//        onWeatherDataImpl=OnWeatherDataImpl
        mainViewModel = MainViewModel()
        responseData = ResponseData(40.7128, -74.0060, "1590740327084", currently)
        responseData = ResponseData(37.3855, -122.08, "1590740327084", currently)
        mainViewModel.locationCollection.add(Location("40.7128","-74.0060"))
        mainViewModel.locationCollection.add(Location("37.3855", "-122.08"))
//        mainViewModel.zipResponse(onWeatherDataImpl)
        list.add(responseData)
    }

    @Test
    fun zipResponse_fail() {

        Mockito.verify(onWeatherDataImpl)
            .onFailure("")
    }

    @Test
    fun zipResponse_success() {
        val observable = Observable.just(responseData)
        Mockito.`when`(mainViewModel.getWeatherResponse("40.7128","-74.0060")).thenReturn(observable)
//        mainViewModel.getWeatherResponse(list)
//        Mockito.verify(onWeatherDataImpl).onSuccess(list);
//        Assert.assertEquals(list.size,0)
    }

    @Test
    fun getWeatherResponse() {
//        Mockito.`when`(mRepository.fetchWeather("32.7864","34.565")).thenReturn()
    }
}
package com.kat.weather.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kat.weather.R
import com.kat.weather.adapter.WeatherListAdapter
import com.kat.weather.fragment.DetailFragment
import com.kat.weather.interfaces.OnWeatherDataImpl
import com.kat.weather.model.Location
import com.kat.weather.model.ResponseData
import com.kat.weather.network.ConnectivityHelper
import com.kat.weather.network.ConnectivityReceiver
import com.kat.weather.fragment.BaseFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.main_fragment.*

/**
 * Fragment to display list of city's weather
 */
class MainFragment : BaseFragment(), ConnectivityReceiver.ConnectivityReceiverListener {

    private lateinit var weatherListAdapter: WeatherListAdapter
    private lateinit var compositeDisposable: CompositeDisposable
    private val locationLA = Location("40.7128", "-74.0060")//Hardcoded for a moment
    private val locationNY = Location("37.3855", "-122.08")//Hardcoded for a moment
    private lateinit var viewModel: MainViewModel
    private var hasConnection: Boolean? = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView!!.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        init()
        swipeRefreshLayout.setOnRefreshListener {
            callMergeReponses()
        }
    }

    override fun onStart() {
        super.onStart()
        checkConditionBeforeAPICall()
        setWeatherAdapter(recyclerView)
    }

    /**
     * Setting weather  adapter class based on observer
     */
    private fun setWeatherAdapter(recyclerView: RecyclerView) {
        viewModel.listOfData.observe(
            viewLifecycleOwner,
            Observer {
                weatherListAdapter = WeatherListAdapter(it)
                recyclerView.adapter = weatherListAdapter
                clickListener()
            })
    }

    /**
     * Initialization of objects
     */
    private fun init() {
        compositeDisposable = CompositeDisposable()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        hasConnection = ConnectivityHelper().isConnectingToInternet(requireContext())
        viewModel.locationCollection.add(locationLA)
        viewModel.locationCollection.add(locationNY)
    }

    /**
     * Method to merge responses
     */
    private fun callMergeReponses() {
        when {
            hasConnection!! -> {
                startSwipeRefresh()
                viewModel.zipResponse(object :
                    OnWeatherDataImpl {
                    override fun onSuccess(data: List<ResponseData>) {
                        stopSwipeRefresh()
                    }

                    override fun onFailure(error: String) {
                        stopSwipeRefresh()
                        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }

    /**
     * Click listener for clicked item in list of weather
     */
    private fun clickListener() {
        weatherListAdapter.setItemClickListener(object :
            WeatherListAdapter.ItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val newFragment =
                    DetailFragment.newInstance(
                        viewModel.listOfData.value!![position]
                    )
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.container, newFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })
    }

    /**
     * Method to listen change in network
     */
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        when {
            isConnected -> {
                hasConnection = isConnected
                checkConditionBeforeAPICall()
            }
        }
    }

    /**
     * Method to check all conditions prior to get weather data
     */
    private fun checkConditionBeforeAPICall() {
        showMessage(hasConnection!!)
        if (!viewModel.isDataAvailabile!! &&
            hasConnection!!
        ) {
            callMergeReponses()
        }
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onDestroy() {
        viewModel.disposeAll()
        super.onDestroy()
    }
}

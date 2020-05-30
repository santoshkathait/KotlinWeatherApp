package com.kat.weather.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.kat.weather.R
import com.kat.weather.databinding.DetailsFragmentBinding
import com.kat.weather.model.ResponseData

/**
 * Fragment to display city's weather details
 */
class DetailFragment : BaseFragment() {

    private lateinit var responseData: ResponseData

    companion object {

        const val KEY_PRODUCT = "KEY_PRODUCT"

        /**
         * New instance with is recieving data send from Main fragment
         *
         * @param responseData weather response data format
         * @return DetailFragment
         */
        fun newInstance(responseData: ResponseData): DetailFragment {
            val args = Bundle()
            args.putSerializable(KEY_PRODUCT, responseData)
            val fragment = DetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { responseData = it.getSerializable(KEY_PRODUCT) as ResponseData }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: DetailsFragmentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(container?.context), R.layout.details_fragment, container,
            false
        )
        binding.datas = responseData
        return binding.root
    }
}
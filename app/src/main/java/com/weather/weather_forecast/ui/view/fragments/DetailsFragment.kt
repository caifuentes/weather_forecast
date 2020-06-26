package com.weather.weather_forecast.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.weather.weather_forecast.data.api.Result
import com.weather.weather_forecast.databinding.FragmentDetailsBinding
import com.weather.weather_forecast.di.utils.Injectable
import com.weather.weather_forecast.di.utils.injectViewModel
import com.weather.weather_forecast.ui.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import timber.log.Timber
import javax.inject.Inject

class DetailsFragment : Fragment(), Injectable {

    private val args: DetailsFragmentArgs by navArgs()

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        context ?: return binding.root

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        swipeRefreshLayout.setOnRefreshListener {
            getDetails()
        }
        viewModel = injectViewModel(viewModelFactory)
        getDetails()
    }

    private fun getDetails() {
        viewModel.getDetails(args.id).observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    swipeRefreshLayout.isRefreshing = false
                    if (result.data != null) {
                        binding.apply {
                            faveClick = createOnClickListener()
                            details = result.data
                            executePendingBindings()
                        }
                    }
                    Timber.e("Result Success")
                }
                Result.Status.LOADING -> {
                    swipeRefreshLayout.isRefreshing = true
                    Timber.e("Loading")
                }
                Result.Status.ERROR -> {
                    swipeRefreshLayout.isRefreshing = false
                    Timber.e("Result Error ${result.message}")
                }
            }
        })
    }

    private fun createOnClickListener(): View.OnClickListener {
        return View.OnClickListener {

        }
    }
}
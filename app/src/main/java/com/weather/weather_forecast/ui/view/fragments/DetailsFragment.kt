package com.weather.weather_forecast.ui.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.weather.weather_forecast.R
import com.weather.weather_forecast.data.api.Result
import com.weather.weather_forecast.databinding.FragmentDetailsBinding
import com.weather.weather_forecast.di.utils.Injectable
import com.weather.weather_forecast.di.utils.injectViewModel
import com.weather.weather_forecast.ui.viewmodel.DetailsViewModel
import com.weather.weather_forecast.utils.DialogListener
import com.weather.weather_forecast.utils.DialogUtils
import com.weather.weather_forecast.utils.NetworkUtils
import com.weather.weather_forecast.utils.SharedPrefUtils
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
        img_favorite.setOnClickListener {
            SharedPrefUtils.updateFavorites(args.id.toString())
            configureFavorites()
        }
        viewModel = injectViewModel(viewModelFactory)
        getDetails()
        configureFavorites()
    }

    private fun getDetails() {
        if (!NetworkUtils.isInternetAvailable(activity as Context)) {
            DialogUtils.showMessageDialog(activity as Context,
                requireActivity().getString(R.string.no_internet_connection),
                requireActivity().getString(R.string.no_internet_connection_msg),
                requireActivity().getString(R.string.ok), listener = object : DialogListener {
                    override fun onPositiveButtonClick() {
                        getDetails()
                    }

                    override fun onNegativeButtonClick() {}
                })
            return
        }

        viewModel.getDetails(args.id).observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    swipeRefreshLayout.isRefreshing = false
                    if (result.data != null) {
                        binding.apply {
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
                    Toast.makeText(activity, result.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun configureFavorites() {
        val faves = SharedPrefUtils.getFavorites()
        img_favorite.setImageResource(
            if (faves.isNotEmpty() && args.id.toString() in faves) {
                R.drawable.ic_favorite_black
            } else {
                R.drawable.ic_favorite_border_black
            }
        )
    }
}
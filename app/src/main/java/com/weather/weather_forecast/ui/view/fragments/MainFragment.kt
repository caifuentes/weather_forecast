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
import androidx.recyclerview.widget.LinearLayoutManager
import com.weather.weather_forecast.R
import com.weather.weather_forecast.data.api.Result
import com.weather.weather_forecast.di.utils.Injectable
import com.weather.weather_forecast.di.utils.injectViewModel
import com.weather.weather_forecast.ui.view.adapters.ItemAdapter
import com.weather.weather_forecast.ui.viewmodel.ListViewModel
import com.weather.weather_forecast.utils.DialogListener
import com.weather.weather_forecast.utils.DialogUtils
import com.weather.weather_forecast.utils.NetworkUtils
import kotlinx.android.synthetic.main.fragment_main.*
import timber.log.Timber
import javax.inject.Inject

class MainFragment : Fragment(), Injectable {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ListViewModel
    private lateinit var adapter: ItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        adapter = ItemAdapter()
        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL, false
        )
        recyclerView.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener {
            getList()
        }

        viewModel = injectViewModel(viewModelFactory)

        getList()
    }

    private fun getList() {
        if (!NetworkUtils.isInternetAvailable(activity as Context)) {
            DialogUtils.showMessageDialog(activity as Context,
                requireActivity().getString(R.string.no_internet_connection),
                requireActivity().getString(R.string.no_internet_connection_msg),
                requireActivity().getString(R.string.ok), listener = object : DialogListener {
                    override fun onPositiveButtonClick() {
                        getList()
                    }

                    override fun onNegativeButtonClick() {}
                })
            return
        }

        viewModel.getList("1701668,3067696,1835848").observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    swipeRefreshLayout.isRefreshing = false
                    if (result.data != null) {
                        result.data.let { adapter.submitList(it.list) }
                    }
                    Timber.e("Result Success ${result.data?.list?.size}")
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
}
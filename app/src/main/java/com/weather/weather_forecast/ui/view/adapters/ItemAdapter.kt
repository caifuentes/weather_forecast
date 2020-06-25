package com.weather.weather_forecast.ui.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weather.weather_forecast.R
import com.weather.weather_forecast.data.models.Details
import com.weather.weather_forecast.databinding.AdapterItemBinding
import com.weather.weather_forecast.ui.view.fragments.DetailsFragment

class ItemAdapter :  ListAdapter<Details, ItemAdapter.ItemViewHolder>(
    object : DiffUtil.ItemCallback<Details>() {
        override fun areItemsTheSame(old: Details, new: Details): Boolean =
            old.id == new.id

        override fun areContentsTheSame(old: Details, new: Details): Boolean =
            old == new
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(AdapterItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false), parent.context)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val record = getItem(position)
        holder.bind(createOnClickListener(record.id), record)
    }

    private fun createOnClickListener(id: Int): View.OnClickListener {
        return View.OnClickListener {
            val detailsFragment = DetailsFragment.newInstance(id)
//            it.findNavController().navigate(detailsFragment)
        }
    }

    inner class ItemViewHolder(private val binding: AdapterItemBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, itemDetail: Details) {
            binding.apply {

                cardView.setCardBackgroundColor(
                    when {
                        itemDetail.main.temp < 0 -> { context.resources.getColor(R.color.colorFreezing) }
                        itemDetail.main.temp < 15 -> { context.resources.getColor(R.color.colorCold) }
                        itemDetail.main.temp < 30 -> { context.resources.getColor(R.color.colorWarm) }
                        else -> { context.resources.getColor(R.color.colorHot) }
                    }
                )

                clickListener = listener
                details = itemDetail
                executePendingBindings()
            }
        }
    }
}
package com.example.app10x.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app10x.view.MainActivity
import com.example.app10x.R
import com.example.app10x.dataModel.ListClass
import com.example.app10x.utility.Utils
import kotlinx.android.synthetic.main.forecast_item.view.*
import java.util.*
import kotlin.collections.HashSet


class ListAdapter(val context: MainActivity) : RecyclerView.Adapter<ListAdapter.DataViewHolder>() {

    private var forecastList: MutableList<ListClass>? = mutableListOf()
    private var daysList: HashSet<Int> = HashSet()

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(forecast: ListClass?) {
            try {
                val calendar = Utils.convertDateFormat(forecast?.dtTxt ?: "")
                val mDay = calendar?.get(Calendar.DAY_OF_WEEK)
                itemView.dayNameTV?.text = when (mDay) {
                    Calendar.SUNDAY -> {
                        context.getString(R.string.sunday)
                    }
                    Calendar.MONDAY -> {
                        context.getString(R.string.monday)
                    }
                    Calendar.TUESDAY -> {
                        context.getString(R.string.tuesday)
                    }
                    Calendar.WEDNESDAY -> {
                        context.getString(R.string.wednesday)
                    }
                    Calendar.THURSDAY -> {
                        context.getString(R.string.thursday)
                    }
                    Calendar.FRIDAY -> {
                        context.getString(R.string.friday)
                    }
                    Calendar.SATURDAY -> {
                        context.getString(R.string.saturday)
                    }
                    else -> {
                        context.getString(R.string.sunday)
                    }
                }
                itemView.dayTempTV?.text = Utils.convertKtoC(forecast?.main?.temp, true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.forecast_item, parent,
                false
            )
        )

    override fun getItemCount(): Int = forecastList?.size?: 0

    override fun onBindViewHolder(holder: ListAdapter.DataViewHolder, position: Int) =
        holder.bind(forecastList?.get(position))

    fun addData(list: MutableList<ListClass>) {
        forecastList?.clear()
        forecastList?.addAll(list)
    }
}
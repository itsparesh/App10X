package com.example.app10x.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app10x.R
import com.example.app10x.view.adapter.ListAdapter
import com.example.app10x.dataModel.ListClass
import com.example.app10x.utility.Status
import com.example.app10x.utility.Utils
import com.example.app10x.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.forecast_bottom_sheet_layout.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: ListAdapter

    companion object {
        val daysList: HashSet<Int> = HashSet()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setLoaderAnimation()
        setupObserver()
        setListUI()
        setClickListener()
    }

    private fun setLoaderAnimation() {
        val loaderAnimator = ObjectAnimator.ofFloat(loader, View.ROTATION, 720f)
        loaderAnimator.repeatCount = Animation.INFINITE
        loaderAnimator.repeatMode = ObjectAnimator.RESTART
        loaderAnimator.duration = 3000
        loaderAnimator.start()
    }

    private fun setLocationTextAnimation() {
        val fadeIn = ObjectAnimator.ofFloat(locationTV, "alpha", 0f, 1f)
        fadeIn.duration = 2000
        val mAnimationSet = AnimatorSet()
        mAnimationSet.play(fadeIn)
        mAnimationSet.start()
    }

    private fun setClickListener() {
        retryBtn?.setOnClickListener {
            errorLayout?.visibility = View.GONE
            loader.visibility = View.VISIBLE
            mainViewModel.fetchCurrentTemperature()
            mainViewModel.fetchForecast()
        }
    }

    private fun setupObserver() {
        mainViewModel.getCurrentTemperature().observe(this, {
            val currentTemperature = it
            when (currentTemperature.status) {
                Status.SUCCESS -> {
                    //loader?.visibility = View.GONE
                    mainTemperatureTV?.text =
                        Utils.convertKtoC(currentTemperature?.data?.main?.temp).plus(
                            getString(
                                R.string.degree
                            )
                        )
                    setLocationTextAnimation()
                    locationTV?.text = currentTemperature?.data?.name
                }
                Status.LOADING -> {
                    loader?.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    loader?.visibility = View.GONE
                    errorLayout?.visibility = View.VISIBLE
                }
            }
        })

        mainViewModel.getForecast().observe(this, {
            val currentTemperature = it
            when (currentTemperature.status) {
                Status.SUCCESS -> {
                    val aniSlide: Animation =
                        AnimationUtils.loadAnimation(applicationContext, R.anim.slide_up)
                    loader?.visibility = View.GONE
                    forecastBottomSheet?.startAnimation(aniSlide)
                    renderList(it?.data?.list as ArrayList<ListClass>?)
                }
                Status.LOADING -> {
                    loader?.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    loader?.visibility = View.GONE
                    errorLayout?.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setListUI() {
        forecastList?.layoutManager = LinearLayoutManager(this)
        adapter = ListAdapter(this)
        forecastList?.adapter = adapter
    }

    private fun renderList(forecastList: ArrayList<ListClass>?) {
        val tempListClass = ArrayList<ListClass>()
        if (forecastList != null) {
            for (forecast in forecastList) {
                try {
                    val calendar = Utils.convertDateFormat(forecast.dtTxt ?: "")
                    val mDay = calendar?.get(Calendar.DAY_OF_WEEK)
                    if (!daysList.contains(mDay)) {
                        mDay?.let { daysList.add(it) }
                        tempListClass.add(forecast)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        tempListClass.let { adapter.addData(it.toMutableList()) }
        adapter.notifyDataSetChanged()
    }
}
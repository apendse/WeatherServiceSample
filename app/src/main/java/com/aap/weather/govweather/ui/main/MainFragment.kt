package com.aap.weather.govweather.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.aap.weather.govweather.R
import com.aap.weather.govweather.databinding.MainFragmentBinding
import com.aap.weather.govweather.ui.main.data.PeriodDataVO
import com.aap.weather.govweather.ui.main.data.WeatherDataVO

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var adapter: WeatherForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.fetchHourlyWeather().observe(this.viewLifecycleOwner) {
            updateForecast(it)
        }
        viewModel.fetchLocation().observe(this.viewLifecycleOwner) {
            updateLocation(it)
        }
        viewModel.error.observe(this.viewLifecycleOwner) {
            showErrorToast(it)
        }
    }

    private fun updateForecast(list: List<PeriodDataVO>) {
        adapter.setData(list)
    }

    private fun updateLocation(weatherDataVO: WeatherDataVO) {
        binding.value.text = getString(R.string.location_unavailable)
        val city = weatherDataVO.properties?.relativeLocation?.properties?.city ?: ""
        val state = weatherDataVO.properties?.relativeLocation?.properties?.state ?: ""
        if (city.isNotEmpty() || state.isNotEmpty()) {
            binding.value.text = getString(R.string.location_template, city, state)
        }
    }

    private fun showErrorToast(error: String?) {
        Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
    }
    private fun initList() {
        this.adapter = WeatherForecastAdapter()
        with(binding.list) {
            layoutManager = LinearLayoutManager(activity)
            this.adapter = this@MainFragment.adapter
        }
    }

}
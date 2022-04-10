package com.aap.weather.govweather.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aap.weather.govweather.R
import com.aap.weather.govweather.databinding.MainFragmentBinding
import com.aap.weather.govweather.ui.main.data.LocationVO
import com.aap.weather.govweather.ui.main.viewmodel.MainViewModel

interface LocationClickListener {
    fun onClicked(locationVO: LocationVO)
}
class MainFragment : Fragment(), LocationClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var adapter: LocationListAdapter

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
        viewModel.fetchList().observe(this.viewLifecycleOwner) {
            updateList(it)
        }
    }

    private fun initList() {
        adapter = LocationListAdapter(this)
        with(binding.locations) {
            layoutManager = LinearLayoutManager(context)
            adapter = this@MainFragment.adapter
        }

    }

    private fun updateList(list: List<LocationVO>) {
        adapter.setData(list)
    }

    override fun onClicked(locationVO: LocationVO) {
        val bundle = Bundle().apply {
            //putFloat(LATITUDE, locationVO.latitude.toFloat())
            //putFloat(LONGITUDE, locationVO.longitude.toFloat())
        }
        val m = MainFragmentDirections.actionMainFragmentToDetailFragment(latitude = locationVO.latitude.toFloat(),
                                                    longitude = locationVO.longitude.toFloat())
        findNavController().navigate(m)
    }

}
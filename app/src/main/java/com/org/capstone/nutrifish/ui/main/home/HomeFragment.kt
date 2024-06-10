package com.org.capstone.nutrifish.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.org.capstone.nutrifish.R
import com.org.capstone.nutrifish.adapter.CarouselAdapter
import com.org.capstone.nutrifish.data.local.entity.FishEntity
import com.org.capstone.nutrifish.databinding.FragmentHomeBinding
import com.org.capstone.nutrifish.utils.Utils
import com.org.capstone.nutrifish.utils.ViewModelFactory


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var carouselAdapter: CarouselAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setViewModel()



        return binding.root
    }

    private fun setViewModel() {
        val viewModelFactory = ViewModelFactory(requireContext().applicationContext)
        homeViewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        homeViewModel.getAllFish { fishList ->
            setCarousel(fishList)
        }

        homeViewModel.loading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setCarousel(fishList: List<FishEntity>) {
        activity?.runOnUiThread {
            binding.carouselRv.apply {
                layoutManager = LinearLayoutManager(
                    requireContext().applicationContext,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                carouselAdapter = CarouselAdapter(fishList).apply {
                    setOnItemClickCallback(object : Utils.OnItemClickCallback {
                        override fun onItemClicked(data: FishEntity) {
                            val bundle = Bundle().apply {
                                putString(FISH_NAME, data.fishName)
                            }
                            findNavController().navigate(
                                R.id.action_navigation_home_to_navigation_detailFish,
                                bundle
                            )
                        }
                    })
                }
            }
            binding.carouselRv.adapter = carouselAdapter
        }
    }

    private fun showLoading(loading: Boolean) {
        binding.pbCarousel.visibility = if (loading) View.VISIBLE else View.GONE
    }

    companion object {
        val FISH_NAME = "fish_name"
    }


}
package com.org.capstone.nutrifish.ui.main.detail.ikan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.org.capstone.nutrifish.R
import com.org.capstone.nutrifish.adapter.AllStoriesAdapter
import com.org.capstone.nutrifish.adapter.LoadingAdapter
import com.org.capstone.nutrifish.data.local.entity.FishEntity
import com.org.capstone.nutrifish.data.remote.response.ListStoryItem
import com.org.capstone.nutrifish.databinding.FragmentFishDetailBinding
import com.org.capstone.nutrifish.ui.main.home.HomeFragment
import com.org.capstone.nutrifish.ui.main.scan.ScanFragment
import com.org.capstone.nutrifish.utils.SettingPreferences
import com.org.capstone.nutrifish.utils.Utils
import com.org.capstone.nutrifish.utils.ViewModelFactory
import com.org.capstone.nutrifish.utils.dataStore

class FishDetailFragment : Fragment() {
    private var _binding: FragmentFishDetailBinding? = null
    private lateinit var detailFishDetailViewModel: FishDetailViewModel
    private val binding get() = _binding!!
    private lateinit var storyAdapter: AllStoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFishDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        setupUI()

        getDetail()

        return view
    }


    private fun setupUI() {
        with(requireActivity()) {
            findViewById<FloatingActionButton>(R.id.fab_postRecipe).visibility = View.GONE
            findViewById<TextView>(R.id.page_title).apply {
                visibility = View.VISIBLE
                "Fish Detail".also { text = it }
            }
            findViewById<BottomNavigationView>(R.id.bottom_navbar).visibility = View.GONE
            findViewById<FloatingActionButton>(R.id.bt_scan).visibility = View.GONE
            findViewById<TextView>(R.id.top_title).text = ""
            findViewById<ImageButton>(R.id.bt_back).visibility = View.VISIBLE
            findViewById<ImageButton>(R.id.bt_back).setOnClickListener {
                findNavController().navigate(R.id.action_navigation_detailFish_to_navigation_home)
            }
        }
    }


    private fun getDetail() {
        val dataStore = SettingPreferences.getInstance(requireContext().dataStore)
        val viewModelFactory = ViewModelFactory(this@FishDetailFragment.requireContext(), dataStore)
        detailFishDetailViewModel =
            ViewModelProvider(this, viewModelFactory)[FishDetailViewModel::class.java]

        val fishName = arguments?.getString(ScanFragment.FISH_NAME) ?: arguments?.getString(
            HomeFragment.FISH_NAME
        )
        fishName?.let {
            detailFishDetailViewModel.getFishByName(it) { fishEntity ->
                setView(fishEntity)
                filteredStories(it)
            }
        }

    }

    private fun filteredStories(it: String) {
        storyAdapter = AllStoriesAdapter()
        binding.recipeRecommendRv.apply {
            layoutManager = LinearLayoutManager(requireContext().applicationContext)
            adapter = storyAdapter.withLoadStateFooter(
                footer = LoadingAdapter { storyAdapter.retry() }
            )

        }

        storyAdapter.setOnItemClickCallback(object : Utils.OnItemClickCallback {
            override fun onFishClicked(data: FishEntity) {
                // Do nothing
            }

            override fun onPostClicked(data: ListStoryItem) {
                val showBack = requireActivity().findViewById<ImageButton>(R.id.bt_back)
                showBack.visibility = View.VISIBLE
                val bundle = Bundle().apply {
                    putParcelable(HomeFragment.RECIPE_ITEM, data)
                }
                findNavController().navigate(
                    R.id.action_navigation_detailFish_to_navigation_detailPost, bundle
                )
            }
        })

        val fishName = if (it.contains("Bawal Putih")) "Bawal" else it
        requireActivity().runOnUiThread {
            detailFishDetailViewModel.getRecipeByTitle(fishName).asLiveData().observe(viewLifecycleOwner){pagingData ->
                storyAdapter.submitData(lifecycle, pagingData)
            }
        }
    }

    private fun setView(fishEntity: FishEntity) {
        requireActivity().runOnUiThread {
            Glide.with(requireContext()).load(fishEntity.fishImage)
                .into(binding.ivImageFishPlaceholder)
            binding.tvFishTitle.text = fishEntity.fishName
            binding.benefitBody.text = fishEntity.fishBenefits
            binding.descriptionBody.text = fishEntity.fishDescription
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
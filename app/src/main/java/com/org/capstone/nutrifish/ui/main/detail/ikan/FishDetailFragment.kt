package com.org.capstone.nutrifish.ui.main.detail.ikan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.org.capstone.nutrifish.R
import com.org.capstone.nutrifish.data.local.entity.FishEntity
import com.org.capstone.nutrifish.databinding.FragmentFishDetailBinding
import com.org.capstone.nutrifish.ui.main.home.HomeFragment
import com.org.capstone.nutrifish.ui.main.scan.ScanFragment
import com.org.capstone.nutrifish.utils.SettingPreferences
import com.org.capstone.nutrifish.utils.ViewModelFactory
import com.org.capstone.nutrifish.utils.dataStore

class FishDetailFragment : Fragment() {
    private var _binding: FragmentFishDetailBinding? = null
    private lateinit var detailFishDetailViewModel: FishDetailViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFishDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        hideView()

        getDetail()

        return view
    }



    private fun hideView() {
        val hideFab = requireActivity().findViewById<FloatingActionButton>(R.id.fab_postRecipe)
        hideFab.visibility = View.GONE
        val pageTitle = requireActivity().findViewById<TextView>(R.id.page_title)
        pageTitle.visibility = View.VISIBLE
        "Fish Detail".also { pageTitle.text = it }
        val hideBottomNavigation =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navbar)
        hideBottomNavigation.visibility = View.GONE
        val hideScan = requireActivity().findViewById<FloatingActionButton>(R.id.bt_scan)
        hideScan.visibility = View.GONE
        val titleApp = requireActivity().findViewById<TextView>(R.id.top_title)
        "".also { titleApp.text = it }
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
package com.org.capstone.nutrifish.ui.main.detail.ikan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.org.capstone.nutrifish.R
import com.org.capstone.nutrifish.data.local.entity.FishEntity
import com.org.capstone.nutrifish.databinding.FragmentFishDetailBinding
import com.org.capstone.nutrifish.ui.main.home.HomeFragment
import com.org.capstone.nutrifish.ui.main.scan.ScanFragment
import com.org.capstone.nutrifish.utils.ViewModelFactory

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

        val hideFab = requireActivity().findViewById<FloatingActionButton>(R.id.fab_postRecipe)
        hideFab.visibility = View.GONE

        getDetail()

        return view
    }

    private fun getDetail() {
        val viewModelFactory = ViewModelFactory(this@FishDetailFragment.requireContext())
        detailFishDetailViewModel =
            ViewModelProvider(this, viewModelFactory)[FishDetailViewModel::class.java]

        val fishName = arguments?.getString(ScanFragment.FISH_NAME) ?: arguments?.getString(HomeFragment.FISH_NAME)
        fishName?.let {
            detailFishDetailViewModel.getFishByName(it) {fishEntity ->
                setView(fishEntity)
            }
        }


    }

    private fun setView(fishEntity: FishEntity) {
        requireActivity().runOnUiThread{
            Glide.with(requireContext()).load(fishEntity.fishImage).into(binding.ivImageFishPlaceholder)
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
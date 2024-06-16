package com.org.capstone.nutrifish.ui.main.detail.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.org.capstone.nutrifish.R
import com.org.capstone.nutrifish.data.remote.response.UserStoriesItem
import com.org.capstone.nutrifish.databinding.FragmentMyPostBinding
import com.org.capstone.nutrifish.ui.main.profile.ProfileFragment


class MyPostFragment : Fragment() {
    private var _binding: FragmentMyPostBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyPostBinding.inflate(inflater, container, false)
        setupUI()
        getDetail()
        return binding.root
    }

    private fun setupUI() {
        with(requireActivity()) {
            findViewById<FloatingActionButton>(R.id.fab_postRecipe).visibility = View.GONE
            findViewById<TextView>(R.id.page_title).apply {
                visibility = View.VISIBLE
                "Post".also { text = it }
            }
            findViewById<BottomNavigationView>(R.id.bottom_navbar).visibility = View.GONE
            findViewById<FloatingActionButton>(R.id.bt_scan).visibility = View.GONE
            findViewById<TextView>(R.id.top_title).text = ""
            findViewById<ImageButton>(R.id.bt_back).setOnClickListener {
                handleBackButton()
            }
        }
    }

    private fun handleBackButton() {
        findNavController().navigateUp()
        with(requireActivity()) {
            findViewById<TextView>(R.id.page_title).visibility = View.GONE
            "Profile".also { findViewById<TextView>(R.id.top_title).text = it }
            findViewById<FloatingActionButton>(R.id.bt_scan).visibility = View.VISIBLE
            findViewById<FloatingActionButton>(R.id.fab_postRecipe).visibility = View.VISIBLE
            findViewById<BottomNavigationView>(R.id.bottom_navbar).visibility = View.VISIBLE
            findViewById<ImageButton>(R.id.bt_back).visibility = View.GONE
        }
    }

    @Suppress("DEPRECATION")
    private fun getDetail() {
        val homeData: UserStoriesItem? = arguments?.getParcelable(ProfileFragment.RECIPE_ITEM)
        homeData?.let { setDetails(it) }
    }

    private fun setDetails(homeData: UserStoriesItem) {
        requireActivity().runOnUiThread {
            with(binding) {
                tvRecipeTitle.text = homeData.storyTitle
                tvUsername.text = if (homeData.storyUsername?.contains("@") == true) {
                    "Oleh: @" + homeData.storyUsername.substringBefore("@")
                } else {
                    "Oleh: @${homeData.storyUsername}"
                }
                descriptionBody.text = homeData.storyDescription
                datePosted.text = homeData.storyDateCreated?.substringBefore(",")
                Glide.with(requireContext().applicationContext)
                    .load(homeData.storyPhotoUrl)
                    .into(ivImageRecipePlaceholder)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
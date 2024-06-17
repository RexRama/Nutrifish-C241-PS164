package com.org.capstone.nutrifish.ui.main.detail.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.org.capstone.nutrifish.R
import com.org.capstone.nutrifish.data.remote.response.ListStoryItem
import com.org.capstone.nutrifish.databinding.FragmentDetailPostBinding
import com.org.capstone.nutrifish.ui.main.home.HomeFragment

class DetailPostFragment : Fragment() {

    private var _binding: FragmentDetailPostBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailPostBinding.inflate(inflater, container, false)
        hideView()
        getDetail()
        return binding.root
    }

    private fun hideView() {
        requireActivity().apply {
            findViewById<FloatingActionButton>(R.id.fab_postRecipe).visibility = View.GONE
            findViewById<TextView>(R.id.page_title).apply {
                visibility = View.VISIBLE
                "Post".also { text = it }
            }
            findViewById<BottomNavigationView>(R.id.bottom_navbar).visibility = View.GONE
            findViewById<FloatingActionButton>(R.id.bt_scan).visibility = View.GONE
            findViewById<TextView>(R.id.top_title).text = ""
        }
    }

    @Suppress("DEPRECATION")
    private fun getDetail() {
        val homeData: ListStoryItem? = arguments?.getParcelable(HomeFragment.RECIPE_ITEM)
        setDetails(homeData)
    }

    private fun setDetails(homeData: ListStoryItem?) {
        homeData?.let {
            with(binding) {
                tvRecipeTitle.text = it.storyTitle
                tvUsername.text = if (it.username?.contains("@") == true) {
                    "Oleh: @" + it.username.substringBefore("@")
                } else {
                    "Oleh: @" + it.username
                }
                descriptionBody.text = it.storyDescription
                datePosted.text = it.storyDateCreated.toString().substringBefore(",")
                Glide.with(requireContext().applicationContext)
                    .load(it.storyPhotoUrl)
                    .into(ivImageRecipePlaceholder)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

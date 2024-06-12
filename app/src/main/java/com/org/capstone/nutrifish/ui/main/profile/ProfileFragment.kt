package com.org.capstone.nutrifish.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.org.capstone.nutrifish.adapter.LoadingAdapter
import com.org.capstone.nutrifish.adapter.MyStoryAdapter
import com.org.capstone.nutrifish.data.remote.model.UserModel
import com.org.capstone.nutrifish.databinding.FragmentProfileBinding
import com.org.capstone.nutrifish.utils.SettingPreferences
import com.org.capstone.nutrifish.utils.ViewModelFactory
import com.org.capstone.nutrifish.utils.dataStore


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var myStoryAdapter: MyStoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        setView()

        setStory()

        setViewModel()
        return binding.root
    }

    private fun setViewModel() {
        val dataStore = SettingPreferences.getInstance(requireContext().dataStore)
        val viewModelFactory = ViewModelFactory(requireContext().applicationContext, dataStore)
        profileViewModel = ViewModelProvider(this, viewModelFactory)[ProfileViewModel::class.java]

        profileViewModel.fetchMyStory().observe(viewLifecycleOwner) {
            pagingData -> myStoryAdapter.submitData(lifecycle, pagingData)
        }
    }


    private fun setStory() {
        myStoryAdapter = MyStoryAdapter()
        binding.postRecipeRv.apply {
            layoutManager = LinearLayoutManager(requireContext().applicationContext)
            adapter = myStoryAdapter.withLoadStateFooter(
                footer = LoadingAdapter { myStoryAdapter.retry() }
            )
        }

        myStoryAdapter.addLoadStateListener { loadState ->
            binding.pbStories.isVisible = loadState.source.refresh is LoadState.Loading
            handleEmptyState(loadState)
        }

    }

    private fun handleEmptyState(loadState: CombinedLoadStates) {
        val isEmpty = loadState.refresh is LoadState.NotLoading && myStoryAdapter.itemCount == 0
        binding.postRecipeRv.isVisible = !isEmpty
        binding.emptyPost.isVisible = isEmpty
    }


    @Suppress("DEPRECATION")
    private fun setView() {
        requireActivity().runOnUiThread {
            val user = arguments?.getParcelable<UserModel>("user")
            val username = user?.username
            binding.tvProfileName.text = user?.name
            if (username != null) {
                binding.tvProfileUsername.text =
                    if (username.contains("@")) username.substringBefore("@") else username

            }
        }

    }

}
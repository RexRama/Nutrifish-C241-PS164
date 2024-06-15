package com.org.capstone.nutrifish.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.org.capstone.nutrifish.R
import com.org.capstone.nutrifish.data.remote.response.UserStoriesItem
import com.org.capstone.nutrifish.databinding.RecipeItemBinding
import com.org.capstone.nutrifish.utils.Utils

class MyStoryAdapter :
    PagingDataAdapter<UserStoriesItem, MyStoryAdapter.ListViewHolder>(DIFF_CALLBACK) {
    private var onItemClickCallback: Utils.OnItemClickCallback? = null
    class ListViewHolder(var binding: RecipeItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val myStoryItem = getItem(position)
        val storyDate = myStoryItem?.storyDateCreated
        if (myStoryItem != null) {
            val context = holder.itemView.context
            if (myStoryItem.storyPhotoUrl.isNullOrEmpty()) {
                Glide.with(context)
                    .load(R.drawable.logonutrifish)
                    .into(holder.binding.ivRecipeImage)
            } else {
                Glide.with(context)
                    .load(myStoryItem.storyPhotoUrl)
                    .into(holder.binding.ivRecipeImage)
            }
            holder.apply {
                with(binding) {
                    tvRecipeTitle.text = myStoryItem.storyTitle
                    if (storyDate != null) {
                        dateCreated.text =
                            if (storyDate.contains(",")) storyDate.substringBefore(",") else storyDate
                    }
                    tvRecipeUsername.visibility = View.GONE
                    itemView.setOnClickListener{
                        onItemClickCallback?.onMyPostClicked(myStoryItem)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    fun setOnItemClickCallback(onItemClickCallBack: Utils.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallBack
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserStoriesItem>() {
            override fun areItemsTheSame(
                oldItem: UserStoriesItem,
                newItem: UserStoriesItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: UserStoriesItem,
                newItem: UserStoriesItem
            ): Boolean {
                return oldItem.storyID == newItem.storyID
            }
        }
    }
}
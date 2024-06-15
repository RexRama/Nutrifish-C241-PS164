package com.org.capstone.nutrifish.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.org.capstone.nutrifish.R
import com.org.capstone.nutrifish.data.remote.response.ListStoryItem
import com.org.capstone.nutrifish.databinding.RecipeItemBinding
import com.org.capstone.nutrifish.utils.Utils

class AllStoriesAdapter :
    PagingDataAdapter<ListStoryItem, AllStoriesAdapter.ListViewHolder>(DIFF_CALLBACK) {
    private var onItemClickCallback: Utils.OnItemClickCallback? = null

    class ListViewHolder(var binding: RecipeItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val recipeItem = getItem(position)
        val storyDate = recipeItem?.storyDateCreated
        if (recipeItem != null) {
            val context = holder.itemView.context
            if (recipeItem.storyPhotoUrl.isNullOrEmpty()) {
                Glide.with(context)
                    .load(R.drawable.logonutrifish)
                    .into(holder.binding.ivRecipeImage)
            } else {
                Glide.with(context)
                    .load(recipeItem.storyPhotoUrl)
                    .into(holder.binding.ivRecipeImage)
            }
            holder.apply {
                binding.tvRecipeTitle.text = recipeItem.storyTitle
                binding.tvRecipeUsername.text = recipeItem.username.toString()
                binding.dateCreated.text =
                    if (storyDate?.contains(",") == true) storyDate.substringBefore(",") else storyDate
                itemView.setOnClickListener{
                    onItemClickCallback?.onPostClicked(recipeItem)
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem.storyID == newItem.storyID
            }
        }
    }
}
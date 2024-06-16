package com.org.capstone.nutrifish.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.org.capstone.nutrifish.data.local.entity.FishEntity
import com.org.capstone.nutrifish.databinding.CarouselItemBinding
import com.org.capstone.nutrifish.utils.Utils

class CarouselAdapter(private val fishList: List<FishEntity>) :
    RecyclerView.Adapter<CarouselAdapter.ListViewHolder>() {

    private var onItemClickCallBack: Utils.OnItemClickCallback? = null

    class ListViewHolder(var binding: CarouselItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = CarouselItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return fishList.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val fishList = fishList[position]
        Glide.with(holder.itemView.context)
            .load(fishList.fishImage)
            .into(holder.binding.carouselImage)
        holder.apply {
            binding.fishName.text = fishList.fishName
            itemView.setOnClickListener{
                onItemClickCallBack?.onFishClicked(fishList)
            }
        }
    }


    fun setOnItemClickCallback(onItemClickCallBack: Utils.OnItemClickCallback) {
        this.onItemClickCallBack = onItemClickCallBack
    }


}
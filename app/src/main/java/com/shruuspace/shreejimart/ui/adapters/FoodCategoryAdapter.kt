package com.shruuspace.shreejimart.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shruuspace.shreejimart.R

import com.shruuspace.shreejimart.data.models.FoodCategory
import com.shruuspace.shreejimart.databinding.ItemFoodCategoryBinding
import com.shruuspace.shreejimart.ui.interfaces.FoodCategoryClickListener

class FoodCategoryAdapter(private val clickListener: FoodCategoryClickListener) : ListAdapter<FoodCategory, FoodCategoryAdapter.FoodCategoryViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodCategoryBinding.inflate(inflater, parent, false)
        return FoodCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodCategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
        holder.itemView.setOnClickListener {
            clickListener.onFoodCategoryClicked(category)
        }
    }

    inner class FoodCategoryViewHolder(private val binding: ItemFoodCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: FoodCategory) {
            binding.categoryModel = category

            // Inside your FoodCategoryAdapter's onBindViewHolder method
            Glide.with(binding.ivCategory)
                .load(category.imageResId)
                .placeholder(R.drawable.img_splash_svg)
                .into(binding.ivCategory)
            binding.executePendingBindings()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<FoodCategory>() {
        override fun areItemsTheSame(oldItem: FoodCategory, newItem: FoodCategory): Boolean {
            return oldItem.category == newItem.category
        }

        override fun areContentsTheSame(oldItem: FoodCategory, newItem: FoodCategory): Boolean {
            return oldItem == newItem
        }
    }
}
package com.example.vendoapp.ui.adapter.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vendoapp.R
import com.example.vendoapp.data.model.category.Category

class CategoriesAdapter : ListAdapter<Category, CategoriesAdapter.CategoryViewHolder>(DiffCallback()) {

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgCategory: ImageView = itemView.findViewById(R.id.imgCategory)
        private val txtCategory: TextView = itemView.findViewById(R.id.txtCategory)

        fun bind(item: Category) {
            imgCategory.setImageResource(item.imageRes)
            txtCategory.text = item.title
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

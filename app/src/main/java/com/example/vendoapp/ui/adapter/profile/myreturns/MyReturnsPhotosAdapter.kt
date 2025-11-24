package com.example.vendoapp.ui.adapter.profile.myreturns

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.vendoapp.R
import com.example.vendoapp.data.model.myreturns.MyReturnsPhotosModel
import com.example.vendoapp.databinding.ItemReturnsPhotosBinding

class MyReturnsPhotosAdapter(private val context: Context) : RecyclerView.Adapter<MyReturnsPhotosAdapter.MyReturnsPhotosViewHolder>() {

    private val myReturnsPhotos = ArrayList<MyReturnsPhotosModel>()
    val currentList: List<MyReturnsPhotosModel>
        get() = myReturnsPhotos

    class MyReturnsPhotosViewHolder(val itemReturnsPhotosBinding: ItemReturnsPhotosBinding) :
        ViewHolder(itemReturnsPhotosBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReturnsPhotosViewHolder {
        val view = ItemReturnsPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyReturnsPhotosViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myReturnsPhotos.size
    }

    override fun onBindViewHolder(holder: MyReturnsPhotosViewHolder, position: Int) {
        val itemMyReturnsPhotos = myReturnsPhotos[position]

        holder.itemReturnsPhotosBinding.ivReturns.setImageURI(null)
        itemMyReturnsPhotos.image?.let {
            holder.itemReturnsPhotosBinding.ivReturns.setImageURI(it)
        }
    }

    fun addPhoto(photo: MyReturnsPhotosModel) {
        if (myReturnsPhotos.size >= 3) {
             Toast.makeText(context, context.getString(R.string.toast_max_photos), Toast.LENGTH_SHORT).show()
            return
        }
        myReturnsPhotos.add(photo)
        notifyItemInserted(myReturnsPhotos.size - 1)
    }
}
package com.mauroalexandro.cookpadmobile.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mauroalexandro.cookpadmobile.R

/**
 * Created by Mauro_Chegancas
 */
class CollectionsImageAdapter(private val context: Context, private val items: List<String>) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.collection_image_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageURL = items[position]

        if(imageURL.isNotEmpty())
            Glide
                .with(context)
                .load(imageURL)
                .centerCrop()
                .apply(RequestOptions().override(500, 500))
                .into(holder.collectionImage)
    }

    override fun getItemCount(): Int {
        val itemsArrayList = items as ArrayList<*>
        return itemsArrayList.size
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val collectionImage: ImageView = view.findViewById(R.id.collections_image_row_image)
}
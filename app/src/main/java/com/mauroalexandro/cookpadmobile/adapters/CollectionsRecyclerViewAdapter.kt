package com.mauroalexandro.cookpadmobile.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mauroalexandro.cookpadmobile.R
import com.mauroalexandro.cookpadmobile.models.Collection
import com.mauroalexandro.cookpadmobile.ui.collections.CollectionItemCallback

/**
 * Created by Mauro_Chegancas
 */
class CollectionsRecyclerViewAdapter(
    private val context: Context,
    private val collectionItemCallback: CollectionItemCallback
) : RecyclerView.Adapter<CollectionsRecyclerViewAdapter.CollectionsViewHolder>() {
    private var collectionsList = arrayListOf<Collection>()
    //private var utils: Utils = Utils.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsViewHolder {
        return CollectionsViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.collections_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(collectionsViewHolder: CollectionsViewHolder, position: Int) {
        //Collection
        val collection: Collection = getCollectionsList(position)

        //Collection Title
        collectionsViewHolder.collectionTitle.text = collection.title

        //Collection Description
        collectionsViewHolder.collectionDescription.text = collection.description

        //Collection Recipes Count
        val recipesCountText = context.resources.getString(R.string.recipes_count_text,collection.recipe_count.toString())
        collectionsViewHolder.collectionRecipesCount.text = recipesCountText

        //Collections Image List
        val collectionsImageAdapter = CollectionsImageAdapter(context, collection.preview_image_urls)
        collectionsViewHolder.collectionImageList.adapter = collectionsImageAdapter
        collectionsViewHolder.collectionImageList.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        //OnClick
        collectionsViewHolder.collectionLayout.setOnClickListener {
            collectionItemCallback.collectionItemClick(collection.id)
        }
    }

    override fun getItemCount(): Int {
        return collectionsList.size
    }

    private fun getCollectionsList(position: Int): Collection {
        return collectionsList[position]
    }

    fun setCollections(collectionsList: List<Collection>) {
        if(this.collectionsList.isEmpty())
            this.collectionsList = collectionsList as ArrayList<Collection>
        else
            this.collectionsList.addAll(collectionsList)
        notifyDataSetChanged()
    }

    class CollectionsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val collectionLayout: ConstraintLayout = view.findViewById(R.id.collections_row_layout)
        val collectionTitle: TextView = view.findViewById(R.id.collections_row_title)
        val collectionDescription: TextView = view.findViewById(R.id.collections_row_description)
        val collectionRecipesCount: TextView = view.findViewById(R.id.collections_row_recipe_count)
        val collectionImageList: RecyclerView = view.findViewById(R.id.collections_row_recyclerview)
    }
}
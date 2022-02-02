package com.mauroalexandro.cookpadmobile.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mauroalexandro.cookpadmobile.R
import com.mauroalexandro.cookpadmobile.models.Recipe
import com.mauroalexandro.cookpadmobile.ui.collections.CollectionItemCallback

/**
 * Created by Mauro_Chegancas
 */
class RecipesRecyclerViewAdapter(
    private val context: Context/*,
    private val collectionItemCallback: CollectionItemCallback*/
) : RecyclerView.Adapter<RecipesRecyclerViewAdapter.RecipesViewHolder>() {
    private var recipesList = arrayListOf<Recipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        return RecipesViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.recipe_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(recipesViewHolder: RecipesViewHolder, position: Int) {
        //Recipe
        val recipe: Recipe = getRecipesList(position)

        //Recipe Title
        recipesViewHolder.recipeTitle.text = recipe.title

        //Recipe Image
        if(recipe.image_url.isNotEmpty())
            Glide
                .with(context)
                .load(recipe.image_url)
                .fitCenter()
                .into(recipesViewHolder.recipeImage)

        //OnClick
        /*recipesViewHolder.recipeLayout.setOnClickListener {
            collectionItemCallback.collectionItemClick(recipe.id)
        }*/
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    private fun getRecipesList(position: Int): Recipe {
        return recipesList[position]
    }

    fun setCollections(recipesList: List<Recipe>) {
        if(this.recipesList.isEmpty())
            this.recipesList = recipesList as ArrayList<Recipe>
        else
            this.recipesList.addAll(recipesList)
        notifyDataSetChanged()
    }

    class RecipesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeLayout: ConstraintLayout = view.findViewById(R.id.recipe_row_layout)
        val recipeTitle: TextView = view.findViewById(R.id.recipe_row_title)
        val recipeImage: ImageView = view.findViewById(R.id.recipe_row_Image)
    }
}
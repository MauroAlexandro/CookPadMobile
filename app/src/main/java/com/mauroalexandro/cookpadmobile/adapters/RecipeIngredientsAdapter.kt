package com.mauroalexandro.cookpadmobile.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mauroalexandro.cookpadmobile.R

/**
 * Created by Mauro_Chegancas
 */
class RecipeIngredientsAdapter(private val context: Context, private val items: List<String>) : RecyclerView.Adapter<RecipeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            LayoutInflater.from(context).inflate(R.layout.recipe_ingredients_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val ingredient = items[position]

        holder.recipeIngredientTitle.text = ingredient
    }

    override fun getItemCount(): Int {
        val itemsArrayList = items as ArrayList<*>
        return itemsArrayList.size
    }
}

class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val recipeIngredientTitle: TextView = view.findViewById(R.id.recipe_ingredient_row_title)
}
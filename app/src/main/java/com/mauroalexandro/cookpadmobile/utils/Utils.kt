package com.mauroalexandro.cookpadmobile.utils

import androidx.fragment.app.FragmentActivity
import com.mauroalexandro.cookpadmobile.databinding.FragmentCollectionsBinding
import com.mauroalexandro.cookpadmobile.databinding.FragmentRecipesBinding
import com.mauroalexandro.cookpadmobile.ui.collections.CollectionDetailFragment
import com.mauroalexandro.cookpadmobile.ui.recipes.RecipeDetailFragment

/**
 * Created by Mauro_Chegancas
 */
class Utils {

    companion object {
        private var INSTANCE: Utils? = null
        fun getInstance(): Utils {
            if (INSTANCE == null)
                INSTANCE = Utils()

            return INSTANCE as Utils
        }
    }

    private lateinit var _recipesBinding: FragmentRecipesBinding
    private lateinit var _collectionsBinding: FragmentCollectionsBinding

    /**
     * Call CollectionDetailsFragment
     */
    fun openCollectionDetailsFragment(activity: FragmentActivity, collectionID: Int) {
        val collectionDetailFragment = CollectionDetailFragment(collectionID)
        if(!activity.supportFragmentManager.isDestroyed)
            collectionDetailFragment.show(
                activity.supportFragmentManager,
                "collection_detail_dialog_fragment"
            )
    }

    /**
     * Call RecipesDetailsFragment
     */
    fun openRecipesDetailsFragment(activity: FragmentActivity, recipeID: Int) {
        val recipeDetailFragment = RecipeDetailFragment(recipeID)
        if(!activity.supportFragmentManager.isDestroyed)
            recipeDetailFragment.show(
                activity.supportFragmentManager,
                "recipe_detail_dialog_fragment"
            )
    }

    /**
     * Set Recipes Binding
     */
    fun setRecipesBinding(_binding: FragmentRecipesBinding) {
        _recipesBinding = _binding
    }

    /**
     * Get Recipes Binding
     */
    fun getRecipesBindign(): FragmentRecipesBinding{
        return _recipesBinding
    }

    /**
     * Set Collections Binding
     */
    fun setCollectionsBinding(_binding: FragmentCollectionsBinding) {
        _collectionsBinding = _binding
    }

    /**
     * Get Collections Binding
     */
    fun getCollectionsBindign(): FragmentCollectionsBinding{
        return _collectionsBinding
    }

}
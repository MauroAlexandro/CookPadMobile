package com.mauroalexandro.cookpadmobile.ui.recipes

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mauroalexandro.cookpadmobile.R
import com.mauroalexandro.cookpadmobile.adapters.RecipeIngredientsAdapter
import com.mauroalexandro.cookpadmobile.adapters.StepsRecyclerViewAdapter
import com.mauroalexandro.cookpadmobile.databinding.RecipeDetailRowBinding
import com.mauroalexandro.cookpadmobile.models.Recipe
import com.mauroalexandro.cookpadmobile.network.Status
import com.mauroalexandro.cookpadmobile.utils.Utils

/**
 * Created by Mauro_Chegancas
 */
class RecipeDetailFragment(private val recipeID: Int) : BottomSheetDialogFragment() {
    private lateinit var recipeDetailViewModel: RecipeDetailViewModel
    private lateinit var recipesIngredientsAdapter: RecipeIngredientsAdapter
    private lateinit var stepsRecyclerViewAdapter: StepsRecyclerViewAdapter
    //private lateinit var recipesRecyclerViewAdapter: RecipesRecyclerViewAdapter
    private lateinit var recipe: Recipe
    private var _binding: RecipeDetailRowBinding? = null
    private val binding get() = _binding!!
    private var firstElement = 0
    private var lastElement = 10
    private var listSize = 0
    private var utils: Utils = Utils.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View
    {
        recipeDetailViewModel = ViewModelProvider(this)[RecipeDetailViewModel::class.java]
        recipeDetailViewModel.getRecipeFromService(recipeID)


        //recipesRecyclerViewAdapter = RecipesRecyclerViewAdapter(requireContext(), this)

        _binding = RecipeDetailRowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setViewModel()

        return root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
    {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            BottomSheetBehavior.from<FrameLayout?>(bottomSheet!!).state =
                BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog
    }

    private fun setViewModel() {
        //Get Recipe Detail
        activity?.let { it ->
            recipeDetailViewModel.getRecipe().observe(it, {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.let {
                            recipeFromRecipes -> recipe = recipeFromRecipes
                            loadValuesIntoLayout()
                        }
                        /*binding.collectionsRecipesRecyclerview.visibility = View.VISIBLE
                        binding.collectionsRecipesRecyclerview.adapter = recipesRecyclerViewAdapter
                        binding.collectionsRecipesRecyclerview.layoutManager = LinearLayoutManager(requireContext())*/
                    }
                    Status.LOADING -> {
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                }

            })
        }
    }

    private fun loadValuesIntoLayout() {
        //Title
        binding.recipeDetailRowTitle.text = recipe.title

        //Story
        binding.recipeDetailRowStory.text = recipe.story

        //Image
        if(!recipe.image_url.isNullOrEmpty())
            Glide
                .with(context!!)
                .load(recipe.image_url)
                .fitCenter()
                //.apply(RequestOptions().override(500, 500))
                .into(binding.recipeDetailRowImage)

        //Ingridients Title
        binding.recipeDetailRowIngredientsTitle.text = resources.getString(R.string.ingredients_title)

        //Ingredients List
        recipesIngredientsAdapter = RecipeIngredientsAdapter(requireContext(),recipe.ingredients)
        binding.recipeDetailRowIngredientsRecyclerview.visibility = View.VISIBLE
        binding.recipeDetailRowIngredientsRecyclerview.adapter = recipesIngredientsAdapter
        binding.recipeDetailRowIngredientsRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        //Steps Title
        binding.recipeDetailRowStepsTitle.text = resources.getString(R.string.steps_title)

        //Steps List
        stepsRecyclerViewAdapter = StepsRecyclerViewAdapter(requireContext(),recipe.steps)
        binding.recipeDetailRowStepsRecyclerview.visibility = View.VISIBLE
        binding.recipeDetailRowStepsRecyclerview.adapter = stepsRecyclerViewAdapter
        binding.recipeDetailRowStepsRecyclerview.layoutManager = LinearLayoutManager(requireContext())

       /* if(lastElement <= listSize-1 && recipesRecyclerViewAdapter.itemCount < listSize) {
            if(listSize <= recipesRecyclerViewAdapter.itemCount + 10)
                lastElement = listSize-1

            val sublist = ArrayList(recipes.subList(firstElement, lastElement))
            recipesRecyclerViewAdapter.setCollections(sublist)
        } else
            Toast.makeText(context, resources.getString(R.string.end_of_list_reached), Toast.LENGTH_LONG).show()*/
    }
}
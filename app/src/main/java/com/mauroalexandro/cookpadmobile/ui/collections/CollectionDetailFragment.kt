package com.mauroalexandro.cookpadmobile.ui.collections

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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mauroalexandro.cookpadmobile.R
import com.mauroalexandro.cookpadmobile.adapters.CollectionsRecyclerViewAdapter
import com.mauroalexandro.cookpadmobile.adapters.RecipesRecyclerViewAdapter
import com.mauroalexandro.cookpadmobile.databinding.FragmentCollectionsDetailBinding
import com.mauroalexandro.cookpadmobile.models.Recipes
import com.mauroalexandro.cookpadmobile.network.Status
import com.mauroalexandro.cookpadmobile.ui.recipes.RecipeItemCallback
import com.mauroalexandro.cookpadmobile.utils.Utils

/**
 * Created by Mauro_Chegancas
 */
class CollectionDetailFragment(private val collectionID: Int) : BottomSheetDialogFragment(), RecipeItemCallback {
    private lateinit var collectionsDetailViewModel: CollectionsDetailViewModel
    private lateinit var recipesRecyclerViewAdapter: RecipesRecyclerViewAdapter
    private lateinit var recipes: Recipes
    private var _binding: FragmentCollectionsDetailBinding? = null
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
        collectionsDetailViewModel = ViewModelProvider(this)[CollectionsDetailViewModel::class.java]
        collectionsDetailViewModel.getRecipesFromService(collectionID)

        recipesRecyclerViewAdapter = RecipesRecyclerViewAdapter(requireContext(), this)

        _binding = FragmentCollectionsDetailBinding.inflate(inflater, container, false)
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
        //Get Recipes Detail
        activity?.let { it ->
            collectionsDetailViewModel.getRecipes().observe(it, {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        it.data?.let {
                            recipesFromCollection -> recipes = recipesFromCollection
                            listSize = this.recipes.size
                            lastElement = listSize-1
                            loadValuesIntoLayout()
                        }
                        binding.collectionsRecipesRecyclerview.visibility = View.VISIBLE
                        binding.collectionsRecipesRecyclerview.adapter = recipesRecyclerViewAdapter
                        binding.collectionsRecipesRecyclerview.layoutManager = LinearLayoutManager(requireContext())
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.collectionsRecipesRecyclerview.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                }

            })
        }
    }

    private fun loadValuesIntoLayout() {
        if(lastElement <= listSize-1 && recipesRecyclerViewAdapter.itemCount < listSize) {
            if(firstElement >= lastElement) {
                firstElement = 0
                lastElement = listSize - 1
            }

            if(listSize <= recipesRecyclerViewAdapter.itemCount + 10)
                lastElement = listSize-1

            val sublist = ArrayList(recipes.subList(firstElement, lastElement))
            recipesRecyclerViewAdapter.setCollections(sublist)
        } else
            Toast.makeText(context, resources.getString(R.string.end_of_list_reached), Toast.LENGTH_LONG).show()
    }

    override fun recipeItemClick(recipeID: Int) {
        this.dismiss()
        utils.openRecipesDetailsFragment(requireActivity(), recipeID)
    }
}
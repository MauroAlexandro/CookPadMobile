package com.mauroalexandro.cookpadmobile.ui.recipes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mauroalexandro.cookpadmobile.adapters.RecipesRecyclerViewAdapter
import com.mauroalexandro.cookpadmobile.databinding.FragmentRecipesBinding
import com.mauroalexandro.cookpadmobile.models.Recipes
import com.mauroalexandro.cookpadmobile.network.Status
import com.mauroalexandro.cookpadmobile.utils.Utils

class RecipesFragment : Fragment(), RecipeItemCallback {
    private lateinit var recipesViewModel: RecipesViewModel
    private var _binding: FragmentRecipesBinding? = null
    private lateinit var recipesRecyclerViewAdapter: RecipesRecyclerViewAdapter
    private lateinit var recipes: Recipes
    private var firstElement = 0
    private var lastElement = 10
    private var listSize = 0
    private var utils: Utils = Utils.getInstance()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        recipesViewModel =
            ViewModelProvider(this)[RecipesViewModel::class.java]
        recipesViewModel.getRecipesFromService()

        recipesRecyclerViewAdapter = RecipesRecyclerViewAdapter(requireContext(), this)

        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        utils.setRecipesBinding(_binding!!)

        setViewModel()

        return root
    }

    override fun onAttach(context: Context) {
        firstElement = 0
        lastElement = 10
        super.onAttach(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setViewModel() {
        //Get Recipes
        activity?.let { it ->
            recipesViewModel.getRecipes().observe(it, {
                when (it.status) {
                    Status.SUCCESS -> {
                        if(_binding == null)
                            _binding = utils.getRecipesBindign()
                        binding.progressBar.visibility = View.GONE
                        it.data?.let {
                            recipes -> this.recipes = recipes
                            listSize = this.recipes.size
                            lastElement = listSize-1
                            loadValuesIntoLayout()
                        }
                        binding.collectionsRecipesRecyclerview.visibility = View.VISIBLE
                        binding.collectionsRecipesRecyclerview.adapter = recipesRecyclerViewAdapter
                        binding.collectionsRecipesRecyclerview.layoutManager = LinearLayoutManager(activity)
                    }
                    Status.LOADING -> {
                        if(_binding == null)
                            _binding = utils.getRecipesBindign()
                        binding.progressBar.visibility = View.VISIBLE
                        binding.collectionsRecipesRecyclerview.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        if(_binding == null)
                            _binding = utils.getRecipesBindign()
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }

    private fun loadValuesIntoLayout() {
        if(lastElement <= listSize-1 && recipesRecyclerViewAdapter.itemCount < listSize-1) {
            if(firstElement >= lastElement) {
                firstElement = 0
                lastElement = listSize - 1
            }

            if(listSize <= recipesRecyclerViewAdapter.itemCount + 10)
                lastElement = listSize-1

            val sublist = ArrayList(recipes.subList(firstElement, lastElement))
            recipesRecyclerViewAdapter.setCollections(sublist)
        }
    }

    override fun recipeItemClick(recipeID: Int) {
        utils.openRecipesDetailsFragment(requireActivity(), recipeID)
    }
}
package com.mauroalexandro.cookpadmobile.ui.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mauroalexandro.cookpadmobile.R
import com.mauroalexandro.cookpadmobile.adapters.CollectionsRecyclerViewAdapter
import com.mauroalexandro.cookpadmobile.databinding.FragmentCollectionsBinding
import com.mauroalexandro.cookpadmobile.models.Collections
import com.mauroalexandro.cookpadmobile.network.Status
import com.mauroalexandro.cookpadmobile.utils.Utils

class CollectionsFragment : Fragment(), CollectionItemCallback {

    private lateinit var collectionsViewModel: CollectionsViewModel
    private lateinit var collectionsRecyclerViewAdapter: CollectionsRecyclerViewAdapter
    private lateinit var collections: Collections
    private var _binding: FragmentCollectionsBinding? = null
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
    ): View? {
        collectionsViewModel =
            ViewModelProvider(this)[CollectionsViewModel::class.java]

        collectionsRecyclerViewAdapter = CollectionsRecyclerViewAdapter(requireContext(), this)

        _binding = FragmentCollectionsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setViewModel()
        collectionsViewModel.getCollectionsFromService()
        /*val userName = utils.getStringSharedPreferencesValue(requireContext(),"userName")
        authoredViewModel.getUserAuthoredCodeChallenges(userName)*/

        binding.collectionsRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && dy > 0) {
                    //Reached the BOTTOM of the List
                    firstElement += 10
                    lastElement += 10
                    loadValuesIntoAdapter()
                }
            }
        })

        return root
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setViewModel() {
        //Get Collections Challenges
        activity?.let { it ->
            collectionsViewModel.getCollections().observe(it, {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        it.data?.let {
                            collections -> this.collections = collections
                            listSize = this.collections.size
                            lastElement = listSize-1
                            loadValuesIntoAdapter()
                        }
                        binding.collectionsRecyclerview.visibility = View.VISIBLE
                        binding.collectionsRecyclerview.adapter = collectionsRecyclerViewAdapter
                        binding.collectionsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.collectionsRecyclerview.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }

    private fun loadValuesIntoAdapter() {
        if(lastElement <= listSize-1 && collectionsRecyclerViewAdapter.itemCount < listSize) {
            if(firstElement >= lastElement) {
                firstElement = 0
                lastElement = listSize - 1
            }

            if(listSize <= collectionsRecyclerViewAdapter.itemCount + 10)
                lastElement = listSize-1

            val sublist = ArrayList(collections.subList(firstElement, lastElement))
            collectionsRecyclerViewAdapter.setCollections(sublist)

        } else
            Toast.makeText(context, resources.getString(R.string.end_of_list_reached), Toast.LENGTH_LONG).show()
    }

    override fun collectionItemClick(collectionID: Int) {
        utils.openCollectionDetailsFragment(requireActivity(), collectionID)
    }
}
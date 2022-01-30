package com.mauroalexandro.cookpadmobile.ui.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mauroalexandro.cookpadmobile.databinding.FragmentCollectionsBinding

class CollectionsFragment : Fragment() {

    private lateinit var collectionsViewModel: CollectionsViewModel
    private var _binding: FragmentCollectionsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        collectionsViewModel =
            ViewModelProvider(this).get(CollectionsViewModel::class.java)

        _binding = FragmentCollectionsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textCollections
        collectionsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
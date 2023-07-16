package com.example.myapplication.ui.search_result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.GourmetListAdapter
import com.example.myapplication.databinding.FragmentSearchResultBinding
import com.example.myapplication.ui.home.HomeViewModel

class SearchResultFragment : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(requireParentFragment()).get(HomeViewModel::class.java)

        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.searchResultTextView
        homeViewModel.searchResultText.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val adapter = GourmetListAdapter()
        binding.searchResultRecyclerView.adapter = adapter
        binding.searchResultRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        homeViewModel.searchedGourmetList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        try {
            homeViewModel.searchGourmet(requireContext(), findNavController())
        }catch (e: Exception){
            this.findNavController().popBackStack()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
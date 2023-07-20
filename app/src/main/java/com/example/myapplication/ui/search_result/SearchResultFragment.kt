package com.example.myapplication.ui.search_result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.GourmetListAdapter
import com.example.myapplication.databinding.FragmentSearchResultBinding
import com.example.myapplication.models.Shop
import com.example.myapplication.ui.home.HomeViewModel

class SearchResultFragment : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel: HomeViewModel by navGraphViewModels(R.id.navigation_home)
//            ViewModelProvider(requireParentFragment()).get(HomeViewModel::class.java)

        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = GourmetListAdapter(GourmetListAdapter.OnClickListener{
            val selectGourmet: Shop = it
            val action = SearchResultFragmentDirections
                .actionSearchResultFragmentToShopInformationFragment2(selectGourmet)
            findNavController().navigate(action)
        })
        binding.searchResultRecyclerView.adapter = adapter
        binding.searchResultRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchResultRecyclerView.setOnScrollChangeListener { v, _, _, _, _ ->
            if (!v.canScrollVertically(1)){
                homeViewModel.searchMoreGourmet()
            }
        }


        homeViewModel.searchedGourmetList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
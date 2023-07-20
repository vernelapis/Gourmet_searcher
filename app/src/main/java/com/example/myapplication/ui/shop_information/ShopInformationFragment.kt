package com.example.myapplication.ui.shop_information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.GourmetListAdapter
import com.example.myapplication.databinding.FragmentSearchResultBinding
import com.example.myapplication.databinding.FragmentShopInformationBinding
import com.example.myapplication.ui.home.HomeViewModel

class ShopInformationFragment : Fragment() {

    private var _binding: FragmentShopInformationBinding? = null

    private val binding get() = _binding!!

    private val args: ShopInformationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val shopInformationViewModel =
            ViewModelProvider(this).get(ShopInformationViewModel::class.java)
//        shopInformationViewModel.gourmetData = args.selectedGourmetData
        val gourmetData = args.selectedGourmetData

        _binding = FragmentShopInformationBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        binding.textView.text = gourmetData.name
        binding.gourmet = gourmetData

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
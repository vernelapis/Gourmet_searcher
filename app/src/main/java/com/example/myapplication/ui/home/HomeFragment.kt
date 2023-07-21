package com.example.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.repository.GPS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val rangeItems = arrayOf("300 m","500 m","1 km","2 km","3 km")
    private val genreItems = arrayOf("指定なし","居酒屋","ダイニングバー・バル","創作料理","和食","洋食","イタリアン・フレンチ","中華",
        "焼肉・ホルモン","韓国料理","アジア・エスニック料理","各国料理","カラオケ・パーティ","バー・カクテル","ラーメン",
        "お好み焼き・もんじゃ","カフェ・スイーツ","その他グルメ")
    private val budgetItems = arrayOf("指定なし","~500円","501～1000円","1001～1500円","1501～2000円","2001～3000円","3001～4000円",
        "4001～5000円","5001～7000円","7001～10000円","10001～15000円","15001～20000円","20001～30000円","30001円~")

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel: HomeViewModel by navGraphViewModels(R.id.navigation_home)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        検索半径スピナー
        val rangeAdapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item,rangeItems)
        rangeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.rangeSpinner.adapter = rangeAdapter
        binding.rangeSpinner.setSelection(homeViewModel.range)
        binding.rangeSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                homeViewModel.range = pos }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

//        ジャンルスピナー
        val genreAdapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item,genreItems)
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.genreSpinner .adapter = genreAdapter
        binding.genreSpinner.setSelection(homeViewModel.genre)
        binding.genreSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                homeViewModel.genre = pos }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

//        予算スピナー
        val budgetAdapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item,budgetItems)
        budgetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.budgetSpinner .adapter = budgetAdapter
        binding.budgetSpinner.setSelection(homeViewModel.budget)
        binding.budgetSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                homeViewModel.budget = pos }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.locateAndSearchButton.setOnClickListener{
            try {
                homeViewModel.searchGourmet(requireContext())
                findNavController().navigate(R.id.action_home_to_searchResult)
            } catch (e:Exception){
                println(e)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
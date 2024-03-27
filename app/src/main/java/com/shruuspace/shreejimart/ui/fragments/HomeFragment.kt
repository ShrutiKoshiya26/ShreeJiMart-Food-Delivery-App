package com.shruuspace.shreejimart.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager

import com.shruuspace.shreejimart.data.models.FoodCategory
import com.shruuspace.shreejimart.databinding.FragmentHomeBinding
import com.shruuspace.shreejimart.ui.adapters.FoodCategoryAdapter
import com.shruuspace.shreejimart.ui.interfaces.FoodCategoryClickListener
import com.shruuspace.shreejimart.utils.Constants.catDrawableIds
import com.shruuspace.shreejimart.utils.Constants.categoryNames

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), FoodCategoryClickListener {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var foodCategories: ArrayList<FoodCategory>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        initializeRVData()


        // Inflate the layout for this fragment
        return binding.root
    }

    private fun initializeRVData() {
        // Add more categories here as needed̵
        foodCategories = ArrayList()
        for (i in categoryNames.indices) {
            foodCategories.add(FoodCategory(categoryNames[i], catDrawableIds[i]))
        }

        val adapter = FoodCategoryAdapter(this)
        adapter.submitList(foodCategories)

        binding.rvCategory.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvCategory.adapter = adapter
    }

    override fun onFoodCategoryClicked(category: FoodCategory) {
        Toast.makeText(requireContext(), "Selected category: ${category.category}", Toast.LENGTH_SHORT).show()
    }


}
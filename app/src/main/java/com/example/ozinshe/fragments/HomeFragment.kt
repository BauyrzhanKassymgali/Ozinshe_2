package com.example.ozinshe.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.ozinshe.R
import com.example.ozinshe.data.OnboardingItem
import com.example.ozinshe.data.OnboardingItemsAdapter
import com.example.ozinshe.databinding.FragmentHomeBinding
import com.example.ozinshe.objects.OnboardingItemList
import com.example.ozinshe.provideNavigationHost

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var onboardingItemsAdapter: OnboardingItemsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnboardingItems()
        binding.dotsIndicator.attachTo(binding.viewPager)
        hideButtonForItems()
        provideNavigationHost()?.apply {
            setNavigationVisibility(false)
        }


        binding.btnSkip.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

    }


    private fun setOnboardingItems(){
        onboardingItemsAdapter = OnboardingItemsAdapter(
            OnboardingItemList.OnboardingItemSlides.map {
                OnboardingItem(
                    it.image,
                    getString(it.title.toInt()),
                    getString(it.description.toInt())
                )
            }
        )
        binding.viewPager.adapter = onboardingItemsAdapter
    }

    private fun hideButtonForItems() {
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0 || position == 1) {
                    binding.btnNext.visibility = View.INVISIBLE
                    binding.btnSkip.visibility = View.VISIBLE
                }
                else if(position == 2 ){
                    binding.btnSkip.visibility = View.INVISIBLE
                    binding.btnNext.visibility = View.VISIBLE
                }
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
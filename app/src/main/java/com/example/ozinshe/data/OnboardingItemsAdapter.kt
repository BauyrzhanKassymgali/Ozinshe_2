package com.example.ozinshe.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ozinshe.databinding.ItemPageBinding

class OnboardingItemsAdapter(private val onboardingItems: List<OnboardingItem>):
    RecyclerView.Adapter<OnboardingItemsAdapter.OnboardingViewHolder>() {
    inner class OnboardingViewHolder(private val binding: ItemPageBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(info: OnboardingItem){
            binding.imgOnbrding.setImageResource(info.image)
            binding.titleTxt.text = (info.title)
            binding.txtDesc.text = (info.description)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        return OnboardingViewHolder(
            ItemPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = onboardingItems.size


    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
         holder.bind(onboardingItems[position])
    }
}
package com.example.ozinshe.fragments.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ozinshe.data.model.MainMoviesResponse
import com.example.ozinshe.data.model.MainMoviesResponseItem
import com.example.ozinshe.databinding.FragmentMainBinding
import com.example.ozinshe.databinding.ItemMainMoviesBinding

class MainMovieAdapter: RecyclerView.Adapter<MainMovieAdapter.MainMovieHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<MainMoviesResponseItem>(){
        override fun areItemsTheSame(
            oldItem: MainMoviesResponseItem,
            newItem: MainMoviesResponseItem
        ): Boolean {
            return oldItem == newItem
        }


        override fun areContentsTheSame(
            oldItem: MainMoviesResponseItem,
            newItem: MainMoviesResponseItem
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)
    fun submitList(listMoviesMain: List<MainMoviesResponseItem>){
        differ.submitList(listMoviesMain)
    }

    private var listenerClickAtItem: RcViewItemClickMainMoviesCallback? = null
    fun setOnMovieClickListener(listenner: RcViewItemClickMainMoviesCallback){
        this.listenerClickAtItem = listenner
    }
    inner class MainMovieHolder(private var binding: ItemMainMoviesBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(mainMovieItem: MainMoviesResponseItem) {
            Glide.with(itemView.context)
                .load(mainMovieItem.link)
                .into(binding.imgMainMovie)

            binding.tvTextTitle.text = mainMovieItem.movie.name
            binding.tvTextDescription.text = mainMovieItem.movie.description
            itemView.setOnClickListener{
                 listenerClickAtItem?.onClick(mainMovieItem.id)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMovieHolder {
        return MainMovieHolder(
            ItemMainMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MainMovieHolder, position: Int) {
        holder.bindItem(differ.currentList[position])
    }
}
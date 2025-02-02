package com.example.ozinshe.fragments.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ozinshe.data.model.MainMoviesResponseItem
import com.example.ozinshe.data.model.Movie
import com.example.ozinshe.data.model.Movy
import com.example.ozinshe.databinding.ItemCategoryMovieBinding
import com.example.ozinshe.databinding.ItemMainMoviesBinding

class MoviesByCategoryMainAdapter: RecyclerView.Adapter<MoviesByCategoryMainAdapter.MoviesByCategoryMainHolder>() {
        private val diffCallback = object : DiffUtil.ItemCallback<Movy>(){
            override fun areItemsTheSame(
                oldItem: Movy,
                newItem: Movy
            ): Boolean {
                return oldItem == newItem
            }


            override fun areContentsTheSame(
                oldItem: Movy,
                newItem: Movy
            ): Boolean {
                return oldItem == newItem
            }

        }
        private val differ = AsyncListDiffer(this, diffCallback)
        fun submitList(listMoviesMain: List<Movy>){
            differ.submitList(listMoviesMain)
        }

        private var listenerClickAtItem: RcViewItemClickMainMoviesCallback? = null
        fun setOnMovieClickListener(listener: RcViewItemClickMainMoviesCallback){
            this.listenerClickAtItem = listener
        }
        inner class MoviesByCategoryMainHolder(private var binding: ItemCategoryMovieBinding): RecyclerView.ViewHolder(binding.root) {
            fun bindItem(movieItem: Movy) {
                Glide.with(itemView.context)
                    .load(movieItem.poster.link)
                    .into(binding.imgCategoryMovie)

                binding.tvTitleFilm.text = movieItem.name
                binding.tvDescriptionFilm.text = movieItem.categories[0].name
                itemView.setOnClickListener{
                    listenerClickAtItem?.onClick(movieItem.id)
                }
            }
        }



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesByCategoryMainHolder {
            return MoviesByCategoryMainHolder(
                ItemCategoryMovieBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int {
            return differ.currentList.size
        }

        override fun onBindViewHolder(holder: MoviesByCategoryMainHolder, position: Int) {
            holder.bindItem(differ.currentList[position])
        }
}

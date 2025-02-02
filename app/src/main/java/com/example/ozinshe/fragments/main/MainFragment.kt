package com.example.ozinshe.fragments.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.ozinshe.data.model.Movy
import com.example.ozinshe.data.model.SharedPref
import com.example.ozinshe.databinding.FragmentMainBinding
import com.example.ozinshe.provideNavigationHost


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainFragmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideNavigationHost()?.apply {
            setNavigationVisibility(true)
        }
        val token = SharedPref(requireContext()).getToken()
        viewModel.getMainMovies(token)

        val adapterMainMovie = MainMovieAdapter()
        binding.rcMainMovies.adapter = adapterMainMovie
        viewModel.mainMoviesResponse.observe(viewLifecycleOwner){
            adapterMainMovie.submitList(it)
        }

        viewModel.getMoviesByCategoryMain(token)
        val adapterMoviesByCategory = MoviesByCategoryMainAdapter()
        viewModel.moviesByCategoryMainModel.observe(viewLifecycleOwner){ list ->
            Log.d("AAX", "Полный ответ от сервера: $list")
            if (list.isNullOrEmpty()|| list[0].movies.isNullOrEmpty()){
                Log.d("AAC", "Данные отсутствуют или некорректны.")
                binding.categoryTitle1Text.text = "Нет данных"
            } else {
                Log.d("AAC", "Получены данные: $list")
                Log.d("AAV", "movies: ${list[0].movies}")
                val firstMovie = list[0].movies[0].name
                binding.categoryTitle1Text.text = firstMovie
                adapterMoviesByCategory.submitList(list[0].movies)
            }
        }
    }
}
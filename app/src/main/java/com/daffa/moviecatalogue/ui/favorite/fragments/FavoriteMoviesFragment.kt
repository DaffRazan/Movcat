package com.daffa.moviecatalogue.ui.favorite.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.daffa.moviecatalogue.databinding.FragmentFavoriteMoviesBinding
import com.daffa.moviecatalogue.ui.detail.DetailFilmActivity
import com.daffa.moviecatalogue.ui.main.movies.MoviesAdapter
import com.daffa.moviecatalogue.viewmodel.ViewModelFactory
import com.daffa.moviecatalogue.viewmodels.DetailFilmViewModel
import com.daffa.moviecatalogue.viewmodels.FavoriteViewModel

class FavoriteMoviesFragment : Fragment() {

    private var _binding: FragmentFavoriteMoviesBinding? = null
    private val favMoviesBinding get() = _binding!!

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteMoviesBinding.inflate(layoutInflater, container, false)
        val view = favMoviesBinding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
            adapter = MoviesAdapter()

            favMoviesBinding.rvFavoriteMovie.layoutManager = LinearLayoutManager(context)
            favMoviesBinding.rvFavoriteMovie.setHasFixedSize(true)
            favMoviesBinding.rvFavoriteMovie.adapter = adapter

            viewModel.getFavoriteMovies.observe(viewLifecycleOwner, { favMovies ->
                if (favMovies != null) {
                    noFilmFound(false)
                    adapter.setMovies(favMovies)
                } else {
                    noFilmFound(true)
                }
            })

            adapter.setOnItemClickCallback(object :
                MoviesAdapter.OnItemClickCallback {
                override fun onItemClicked(id: String) {
                    selectedMovie(id)
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoriteMovies.observe(viewLifecycleOwner, { favMovies ->
            if (favMovies != null) {
                noFilmFound(false)
                adapter.setMovies(favMovies)
            } else {
                noFilmFound(true)
            }
        })
    }

    private fun selectedMovie(id: String) {
        val intent = Intent(context, DetailFilmActivity::class.java)
        intent.putExtra(DetailFilmActivity.EXTRA_FILM, id)
        intent.putExtra(DetailFilmActivity.EXTRA_CATEGORY, DetailFilmViewModel.MOVIE)

        requireActivity().startActivity(intent)
    }

    private fun noFilmFound(state: Boolean) {
        if (state) {
            favMoviesBinding.rvFavoriteMovie.visibility = View.GONE
        } else {
            favMoviesBinding.rvFavoriteMovie.visibility = View.VISIBLE
        }
    }
}
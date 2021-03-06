package com.daffa.moviecatalogue.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.AnimationUtils.loadAnimation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.daffa.moviecatalogue.core.R
import com.daffa.moviecatalogue.core.databinding.ItemsMoviesBinding
import com.daffa.moviecatalogue.core.domain.model.Movie
import com.daffa.moviecatalogue.core.utils.Constants.API_POSTER_PATH

class MoviesAdapter :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private var listMovie: List<Movie> = arrayListOf()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setMovies(movies: List<Movie>) {
        this.listMovie = movies
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(id: String)
    }

    inner class MoviesViewHolder(private val binding: ItemsMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(API_POSTER_PATH + movie.poster_path)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)
                tvMovieTitle.text = movie.title
                tvMovieReleaseDate.text = movie.release_date
                tvMovieRating.text = movie.vote_average.toString()
            }

            itemView.setOnClickListener { onItemClickCallback.onItemClicked(movie.id.toString()) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesViewHolder {
        val itemsMoviesBinding =
            ItemsMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(itemsMoviesBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movies = listMovie[position]
        holder.bind(movies)
    }

    override fun getItemCount() = listMovie.size

}
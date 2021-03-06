package com.daffa.moviecatalogue.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.daffa.moviecatalogue.core.R
import com.daffa.moviecatalogue.core.databinding.ItemsTvshowsBinding
import com.daffa.moviecatalogue.core.domain.model.TvShow
import com.daffa.moviecatalogue.core.utils.Constants.API_POSTER_PATH

class TvShowsAdapter : RecyclerView.Adapter<TvShowsAdapter.TvShowsViewHolder>() {

    private var listTvShow: List<TvShow> = arrayListOf()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setTvShow(tvShows: List<TvShow>) {
        this.listTvShow = tvShows
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(id: String)
    }

    inner class TvShowsViewHolder(private val binding: ItemsTvshowsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShow) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(API_POSTER_PATH + tvShow.poster_path)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)
                tvTvShowTitle.text = tvShow.title
                tvTvShowReleaseDate.text = tvShow.release_date
                tvTvShowRating.text = tvShow.vote_average.toString()
            }
            itemView.setOnClickListener { onItemClickCallback.onItemClicked(tvShow.id.toString()) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvShowsViewHolder {
        val itemsTvshowsBinding =
            ItemsTvshowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowsViewHolder(itemsTvshowsBinding)
    }

    override fun onBindViewHolder(holder: TvShowsViewHolder, position: Int) {
        val tvShows = listTvShow[position]
        holder.bind(tvShows)
    }

    override fun getItemCount() = listTvShow.size
}
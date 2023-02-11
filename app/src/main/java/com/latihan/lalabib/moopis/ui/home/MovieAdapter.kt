package com.latihan.lalabib.moopis.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.latihan.lalabib.moopis.R
import com.latihan.lalabib.moopis.data.local.entity.MoviesEntity
import com.latihan.lalabib.moopis.databinding.ItemMovieBinding
import com.latihan.lalabib.moopis.utils.IMG_URL

class MovieAdapter(private val onItemClick: (MoviesEntity) -> Unit) :
    PagingDataAdapter<MoviesEntity, MovieAdapter.MovieViewHolder>(DIFFUTIL) {

    private object DIFFUTIL: DiffUtil.ItemCallback<MoviesEntity>() {
        override fun areItemsTheSame(oldItem: MoviesEntity, newItem: MoviesEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MoviesEntity, newItem: MoviesEntity): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    class MovieViewHolder(private val binding: ItemMovieBinding, val onItemClick: (MoviesEntity) -> Unit):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(movie: MoviesEntity) {
                binding.apply {
                    tvTitle.text = movie.title
                    Glide.with(itemView.context)
                        .load(IMG_URL + movie.poster_path)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_broken_img))
                        .into(ivPoster)
                }
                itemView.setOnClickListener { onItemClick(movie) }
            }
    }
}
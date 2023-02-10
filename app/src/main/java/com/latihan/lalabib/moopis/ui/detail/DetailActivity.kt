package com.latihan.lalabib.moopis.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.latihan.lalabib.moopis.R
import com.latihan.lalabib.moopis.data.local.entity.MoviesEntity
import com.latihan.lalabib.moopis.databinding.ActivityDetailBinding
import com.latihan.lalabib.moopis.utils.IMG_URL
import com.latihan.lalabib.moopis.utils.Status
import com.latihan.lalabib.moopis.utils.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupData()
    }

    private fun setupView() {
        supportActionBar?.apply {
            title = getString(R.string.detail_title)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        detailViewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
    }

    private fun setupData() {
        val extras = intent.extras
        if (extras != null) {
            val id = extras.getString(EXTRA_DATA)
            if (id != null) {
                detailViewModel.setMoviesData(id)
                detailViewModel.detailMovie.observe(this) { detailMovie ->
                    when(detailMovie.status) {
                        Status.LOADING -> showLoading(true)
                        Status.SUCCESS -> {
                            if (detailMovie != null) {
                                detailMovie.data?.let { populatedDetailMovie(it) }
                                showLoading(false)
                            }
                        }
                        Status.ERROR -> {
                            showLoading(false)
                            Toast.makeText(this@DetailActivity, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun populatedDetailMovie(movie: MoviesEntity) {
        binding.apply {
            tvTitle.text = movie.title
            tvVoteAverage.text = movie.vote_average
            tvReleaseDate.text = movie.release_date
            tvOverview.text = movie.overview

            Glide.with(this@DetailActivity)
                .load(IMG_URL + movie.poster_path)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_broken_img)
                )
                .into(ivPosterImage)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}
package com.latihan.lalabib.moopis.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.latihan.lalabib.moopis.R
import com.latihan.lalabib.moopis.adapter.ReviewAdapter
import com.latihan.lalabib.moopis.data.local.entity.MoviesEntity
import com.latihan.lalabib.moopis.data.remote.response.VideosEntity
import com.latihan.lalabib.moopis.databinding.ActivityDetailBinding
import com.latihan.lalabib.moopis.utils.IMG_URL
import com.latihan.lalabib.moopis.utils.Status
import com.latihan.lalabib.moopis.utils.ViewModelFactory
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var reviewAdapter: ReviewAdapter

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
        reviewAdapter = ReviewAdapter()

        val extras = intent.extras
        if (extras != null) {
            val id = extras.getString(EXTRA_DATA)
            if (id != null) {
                detailViewModel.setMoviesData(id)
                detailViewModel.setReviewsData(id)
                detailViewModel.setVideosData(id)

                detailViewModel.detailMovie.observe(this) { detailMovie ->
                    when (detailMovie.status) {
                        Status.LOADING -> showLoading(true)
                        Status.SUCCESS -> {
                            if (detailMovie != null) {
                                detailMovie.data?.let { populatedDetailMovie(it) }
                                showLoading(false)
                            }
                            detailViewModel.reviewData.observe(this) { reviewData ->
                                if (reviewData.results.isEmpty()) {
                                    binding.apply {
                                        ivEmptyReview.visibility = View.VISIBLE
                                        tvEmptyReview.visibility = View.VISIBLE
                                    }
                                } else {
                                    reviewAdapter.submitList(reviewData.results)
                                }
                            }
                            detailViewModel.videoData.observe(this) { data ->
                                data.results.last().let {
                                    youtubePlayer(it)
                                }
                            }
                        }
                        Status.ERROR -> {
                            showLoading(false)
                            Toast.makeText(
                                this@DetailActivity,
                                getString(R.string.error_message),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
        binding.rvReviews.apply {
            layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = reviewAdapter
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
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_broken_img))
                .into(ivPosterImage)
        }
    }

    private fun youtubePlayer(video: VideosEntity) {
        val youTubePlayerView = binding.youtubePlayer
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.enableAutomaticInitialization = false

        val listener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                // using pre-made custom ui
                val defaultPlayerUiController = DefaultPlayerUiController(youTubePlayerView, youTubePlayer)
                youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)

                // play video
                val videoId = video.key!!
                youTubePlayer.cueVideo(videoId, 0f)
            }
        }
        // disable iframe ui
        val options = IFramePlayerOptions.Builder().controls(0).build()
        youTubePlayerView.initialize(listener, options)
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
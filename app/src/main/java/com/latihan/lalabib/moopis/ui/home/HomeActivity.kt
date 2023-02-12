package com.latihan.lalabib.moopis.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.latihan.lalabib.moopis.R
import com.latihan.lalabib.moopis.adapter.LoadingStateAdapter
import com.latihan.lalabib.moopis.adapter.MovieAdapter
import com.latihan.lalabib.moopis.databinding.ActivityHomeBinding
import com.latihan.lalabib.moopis.ui.detail.DetailActivity
import com.latihan.lalabib.moopis.utils.ViewModelFactory

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupData()
    }

    private fun setupView() {
        supportActionBar?.title = getString(R.string.home_title)
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        homeViewModel = ViewModelProvider(this@HomeActivity, factory)[HomeViewModel::class.java]
    }

    private fun setupData() {
        val movieAdapter = MovieAdapter { movie ->
            Intent(this@HomeActivity, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_DATA, movie.id)
                startActivity(this)
            }
        }

        binding.rvMovies.adapter = movieAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                movieAdapter.retry()
            }
        )

        showLoading(true)
        homeViewModel.movie.observe(this@HomeActivity) {
            movieAdapter.submitData(lifecycle, it)
            showLoading(false)
        }

        binding.apply {
            rvMovies.layoutManager = GridLayoutManager(this@HomeActivity, 2)
            rvMovies.adapter = movieAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.shimmerLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
package com.latihan.lalabib.moopis.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.latihan.lalabib.moopis.R
import com.latihan.lalabib.moopis.databinding.ActivityHomeBinding
import com.latihan.lalabib.moopis.ui.detail.DetailActivity
import com.latihan.lalabib.moopis.utils.Status
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

        homeViewModel.getMovies().observe(this) { movies ->
            if (movies != null) {
                when (movies.status) {
                    Status.LOADING -> {
                        showLoading(true)
                    }
                    Status.SUCCESS -> {
                        showLoading(false)
                        movieAdapter.submitList(movies.data)
                    }
                    Status.ERROR -> {
                        showLoading(false)
                        Toast.makeText(this@HomeActivity, R.string.error_message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
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
package com.uc.week4retrofit.view

import com.uc.week4retrofit.adapter.CompanyLogoAdapter
import com.uc.week4retrofit.adapter.CountryAdapter
import com.uc.week4retrofit.adapter.GenreAdapter
import com.uc.week4retrofit.adapter.LanguageAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.uc.week4retrofit.databinding.ActivityMovieDetailsBinding
import com.uc.week4retrofit.helper.Const
import com.uc.week4retrofit.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetail : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var viewModel: MoviesViewModel
    private lateinit var adapterGenre: GenreAdapter
    private lateinit var adapterLanguage: LanguageAdapter
    private lateinit var adapterCountry: CountryAdapter
    private lateinit var adapterCompany: CompanyLogoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvGenre.visibility = View.INVISIBLE
        binding.rvLanguage.visibility = View.INVISIBLE
        binding.rvProductionCountry.visibility = View.INVISIBLE
        binding.rvProductionCompany.visibility = View.INVISIBLE
        binding.tvTitleMovieDetail.visibility = View.INVISIBLE
        binding.tvOverview.visibility = View.INVISIBLE
        binding.textView.visibility = View.INVISIBLE
        binding.textView2.visibility = View.INVISIBLE
        binding.textView3.visibility = View.INVISIBLE
        binding.textView4.visibility = View.INVISIBLE
        binding.textView5.visibility = View.INVISIBLE
        binding.imgMovieDetail.visibility = View.INVISIBLE

        val movieId = intent.getIntExtra("movie_id", 0)
        Toast.makeText(applicationContext, "Movie ID: $movieId", Toast.LENGTH_SHORT).show()

        viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        viewModel.getMoviedetail(movieId , Const.API_KEY)
        viewModel.movieDetails.observe(this, Observer { response ->

            if (response != null) {
                binding.progressBar.visibility = View.INVISIBLE
                binding.rvGenre.visibility = View.VISIBLE
                binding.rvLanguage.visibility = View.VISIBLE
                binding.rvProductionCompany.visibility = View.VISIBLE
                binding.rvProductionCountry.visibility = View.VISIBLE
                binding.tvOverview.visibility = View.VISIBLE
                binding.tvTitleMovieDetail.visibility = View.VISIBLE
                binding.textView.visibility = View.VISIBLE
                binding.textView2.visibility = View.VISIBLE
                binding.textView3.visibility = View.VISIBLE
                binding.textView4.visibility = View.VISIBLE
                binding.textView5.visibility = View.VISIBLE
                binding.imgMovieDetail.visibility = View.VISIBLE
            }

            binding.tvTitleMovieDetail.apply {
                text = response.title
            }

            binding.tvOverview.apply {
                text = response.overview
            }

            binding.rvGenre.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            adapterGenre = GenreAdapter(response.genres)
            binding.rvGenre.adapter = adapterGenre

            binding.rvLanguage.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            adapterLanguage = LanguageAdapter(response.spoken_languages)
            binding.rvLanguage.adapter = adapterLanguage

            binding.rvProductionCountry.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            adapterCountry = CountryAdapter(response.production_countries)
            binding.rvProductionCountry.adapter = adapterCountry

            binding.rvProductionCompany.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            adapterCompany = CompanyLogoAdapter(response.production_companies)
            binding.rvProductionCompany.adapter = adapterCompany

            Glide.with(applicationContext).load(Const.IMG_URL + response.backdrop_path)
                .into(binding.imgMovieDetail)
        })


    }
}
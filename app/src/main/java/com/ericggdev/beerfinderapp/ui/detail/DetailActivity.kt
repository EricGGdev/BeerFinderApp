package com.ericggdev.beerfinderapp.ui.detail

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ericggdev.beerfinderapp.R
import com.ericggdev.beerfinderapp.databinding.ActivityDetailBinding
import com.ericggdev.beerfinderapp.domain.models.Beer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        intent.extras?.let {
            val beer = it.getSerializable(INTENT_BEER) as Beer
            binding.beer = beer
            loadData(beer)
        }

    }

    private fun loadData(beer: Beer) {
        Glide.with(this)
            .load(beer.imageUrl)
            .placeholder(R.drawable.image_beer_placeholder)
            .into(binding.imageBeer)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedCallback.handleOnBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    companion object {
        const val INTENT_BEER = "INTENT_BEER"
    }
}
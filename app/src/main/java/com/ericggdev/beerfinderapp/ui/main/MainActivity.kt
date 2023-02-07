package com.ericggdev.beerfinderapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ericggdev.beerfinderapp.R
import com.ericggdev.beerfinderapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.activity = this
        binding.lifecycleOwner = this

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setupToolbar()
        initAdapter()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.uiState
                .collect {
                    if (it.items.isNotEmpty())
                        (binding.beerList.adapter as? BeersAdapter)?.submitList(it.items)
                }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initAdapter() {
        val adapter = BeersAdapter { clickedBeer ->
            //TODO goToDetail(clickedBeer.id)
        }
        adapter.setHasStableIds(true)
        binding.beerList.layoutManager = LinearLayoutManager(this)
        binding.beerList.adapter = adapter
    }

     fun checkFloatingMenu() {
        if (viewModel.isFloatingDeployed) {
            binding.floatingButtonTop.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.disappear_up))
            binding.floatingButtonSearch.visibility = View.GONE
            binding.floatingButtonSearch.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.disappear_left))
            binding.floatingButtonTop.visibility = View.GONE
        } else {
            binding.floatingButtonTop.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.appear_up))
            binding.floatingButtonTop.visibility = View.VISIBLE
            binding.floatingButtonSearch.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.appear_left))
            binding.floatingButtonSearch.visibility = View.VISIBLE
        }
        viewModel.isFloatingDeployed = !viewModel.isFloatingDeployed
    }

}
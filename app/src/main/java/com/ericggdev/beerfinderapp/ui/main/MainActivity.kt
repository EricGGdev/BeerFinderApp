package com.ericggdev.beerfinderapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ericggdev.beerfinderapp.R
import com.ericggdev.beerfinderapp.databinding.ActivityMainBinding
import com.ericggdev.beerfinderapp.helpers.extensions.emptyString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
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
        initListeners()
    }

    private fun initListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            if (binding.editTextFinder.text.toString() == String.emptyString()) {
                viewModel.getInitialBeers()
            }else{
                binding.swipeRefresh.isRefreshing = false
            }
        }
        binding.floatingButtonTop.setOnClickListener {
            binding.beerList.smoothScrollToPosition(0)
            checkFloatingMenu()
        }

        binding.floatingButtonSearch.setOnClickListener {
            binding.textLayoutFinder.visibility = if(binding.textLayoutFinder.visibility == View.GONE) View.VISIBLE else View.GONE
            checkFloatingMenu()
        }

        binding.beerList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
                val listSize = (recyclerView.adapter as BeersAdapter).currentList.size

                if (layoutManager.findLastVisibleItemPosition() >= listSize - 2 && !viewModel.isCallInProgress && binding.editTextFinder.text.toString() == String.emptyString()) {
                    viewModel.getNextBeersPage()
                }

            }
        })

        binding.editTextFinder.doOnTextChanged { text, _, _, _ ->
            if (text.toString() != String.emptyString()) {
                viewModel.findBeerByName(text.toString())
            } else {
                viewModel.getInitialBeers()
            }
        }


    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.uiState
                .map { it.isLoading }
                .distinctUntilChanged()
                .collect {
                    binding.beerList.isVisible = !it
                    showShimmerEffect(it)
                }
        }

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

    private fun showShimmerEffect(show: Boolean) {
        if (show) {
            binding.swipeRefresh.isRefreshing = false
            binding.swipeRefresh.visibility = View.GONE
            binding.shimmerViewContainer.visibility = View.VISIBLE
            binding.shimmerViewContainer.startShimmer()
        } else {
            binding.shimmerViewContainer.stopShimmer()
            binding.shimmerViewContainer.visibility = View.GONE
            binding.swipeRefresh.visibility = View.VISIBLE
        }
    }

}
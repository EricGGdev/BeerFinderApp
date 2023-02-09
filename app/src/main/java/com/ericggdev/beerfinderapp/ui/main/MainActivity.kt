package com.ericggdev.beerfinderapp.ui.main

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ericggdev.beerfinderapp.R
import com.ericggdev.beerfinderapp.databinding.ActivityMainBinding
import com.ericggdev.beerfinderapp.databinding.DialogCustomLayoutBinding
import com.ericggdev.beerfinderapp.domain.models.Beer
import com.ericggdev.beerfinderapp.domain.models.Empty
import com.ericggdev.beerfinderapp.domain.models.ResultError
import com.ericggdev.beerfinderapp.domain.models.RetrofitError
import com.ericggdev.beerfinderapp.domain.models.isEmpty
import com.ericggdev.beerfinderapp.helpers.extensions.emptyString
import com.ericggdev.beerfinderapp.helpers.extensions.showErrorDialog
import com.ericggdev.beerfinderapp.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
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
        binding.viewModel = viewModel
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
            } else {
                binding.swipeRefresh.isRefreshing = false
            }
        }
        binding.floatingButtonTop.setOnClickListener {
            binding.beerList.smoothScrollToPosition(0)
            checkFloatingMenu()
        }

        binding.floatingButtonSearch.setOnClickListener {
            binding.textLayoutFinder.visibility = if (binding.textLayoutFinder.visibility == View.GONE) View.VISIBLE else View.GONE
            checkFloatingMenu()
        }

        binding.beerList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
                val listSize = (recyclerView.adapter as BeersAdapter).currentList.size

                if (layoutManager.findLastVisibleItemPosition() >= listSize - 2 && !viewModel.isCallInProgress && viewModel.textToSearch.value == String.emptyString()) {
                    viewModel.getNextBeersPage()
                }
            }
        })


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
                    (binding.beerList.adapter as? BeersAdapter)?.submitList(it.items)
                }
        }

        lifecycleScope.launch {
            viewModel.uiState
                .map { it.error }
                .distinctUntilChanged()
                .collect { error ->
                    if (!error.isEmpty())
                        showErrorDialog(
                            getString(R.string.user_general_error),
                            onPositive = { viewModel.getInitialBeers() },
                            onNegative = {}
                        )
                }
        }


        lifecycleScope.launch {
            viewModel.textToSearch.collect { text ->
                if (text != String.emptyString()) viewModel.findBeerByName(text) else viewModel.getInitialBeers()
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initAdapter() {
        val adapter = BeersAdapter { clickedBeer ->
            goToDetail(clickedBeer)
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

    private fun goToDetail(beer: Beer) {
        startActivity(Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.INTENT_BEER, beer)
        })
    }

}
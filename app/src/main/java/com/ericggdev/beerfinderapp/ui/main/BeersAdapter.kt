package com.ericggdev.beerfinderapp.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ericggdev.beerfinderapp.R
import com.ericggdev.beerfinderapp.databinding.ItemBeerBinding
import com.ericggdev.beerfinderapp.domain.models.Beer
import com.ericggdev.beerfinderapp.helpers.extensions.overrideColor

class BeersAdapter(
    private val onBeerClick: (Beer) -> Unit
) : ListAdapter<Beer, BeersAdapter.ViewHolder>(ListAdapterCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onBeerClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemId(position: Int): Long {
        return currentList[position].id.toLong()
    }

    class ViewHolder private constructor(val context: Context, private val binding: ItemBeerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            beer: Beer,
            onBeerClick: (Beer) -> Unit
        ) {
            binding.cardView.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.recycler_animation)

            binding.beer = beer

            binding.circleColor.background.overrideColor(
                context.getColor(
                    when (beer.attenuation_level) {
                        in 0.0..50.0 -> R.color.light_green
                        in 51.0..75.0 -> R.color.light_yellow
                        in 76.0..Double.MAX_VALUE -> R.color.red
                        else -> R.color.gray
                    }
                )
            )

            Glide.with(context)
                .load(beer.image_url)
                .placeholder(R.drawable.image_beer_placeholder)
                .into(binding.imageBeer)

            binding.cardView.setOnClickListener {
                onBeerClick(beer)
            }

            binding.executePendingBindings()
        }

        companion object {

            private const val STATUS_ALIVE = "Alive"

            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemBeerBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(parent.context, binding)
            }
        }
    }

    class ListAdapterCallback : DiffUtil.ItemCallback<Beer>() {
        override fun areItemsTheSame(
            oldItem: Beer,
            newItem: Beer
        ): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Beer,
            newItem: Beer
        ): Boolean {
            return oldItem == newItem
        }
    }
}
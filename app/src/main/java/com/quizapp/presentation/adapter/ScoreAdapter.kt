package com.quizapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quizapp.databinding.ItemScoreBinding

class ScoreAdapter :
    RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {


    inner class ScoreViewHolder(private val binding: ItemScoreBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind() {

            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val binding =
            ItemScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ScoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {


    }

    override fun getItemCount(): Int = 1
}
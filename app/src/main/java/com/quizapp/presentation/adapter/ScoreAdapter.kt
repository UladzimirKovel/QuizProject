package com.quizapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quizapp.data.entities.ScoreEntity
import com.quizapp.databinding.ItemScoreBinding
import java.text.SimpleDateFormat
import java.util.*

class ScoreAdapter : ListAdapter<ScoreEntity, ScoreAdapter.ScoreViewHolder>(ScoreDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val binding = ItemScoreBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ScoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ScoreViewHolder(
        private val binding: ItemScoreBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(score: ScoreEntity) {
            binding.apply {
                tvUser.text = "User: ${score.userId}"
                tvScore.text = "Score: ${score.score}/${score.totalQuestions}"
                
                // Форматируем дату
                val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                tvDate.text = "Date: ${dateFormat.format(Date(score.timestamp))}"
            }
        }
    }

    private class ScoreDiffCallback : DiffUtil.ItemCallback<ScoreEntity>() {
        override fun areItemsTheSame(oldItem: ScoreEntity, newItem: ScoreEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ScoreEntity, newItem: ScoreEntity): Boolean {
            return oldItem == newItem
        }
    }
}
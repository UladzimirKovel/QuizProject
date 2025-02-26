package com.quizapp.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.quizapp.R
import com.quizapp.data.model.QuizResponse
import com.quizapp.databinding.ItemQuizBinding

class ListAdapter(
    private var questions: List<QuizResponse>,
    private val navController: NavController
) :
    RecyclerView.Adapter<ListAdapter.QuestionViewHolder>() {

    inner class QuestionViewHolder(private val binding: ItemQuizBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(question: QuizResponse) {

            binding.tvText.text = question.description
            binding.tvTitle.text = question.category
            binding.tvQuantityQuestion.text = question.tip
            binding.buttonStart.setOnClickListener {
                navController.navigate(R.id.questionsFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding =
            ItemQuizBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return QuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {

        val question = questions[position]
        holder.bind(question)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun upQuestions(newQuestions: List<QuizResponse>) {
        questions = newQuestions
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = questions.size

}
package com.example.evaluacion_2.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.evaluacion_2.databinding.ItemNewsBinding

class NewsAdapter(
    private val list: List<News>,
    private val onClick: (News) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    inner class NewsHolder(val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.tvTitle.text = news.title
            binding.tvSubtitle.text = news.subtitle

            Glide.with(binding.root.context)
                .load(news.imageUrl)
                .into(binding.ivItemImage)

            binding.root.setOnClickListener { onClick(news) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bind(list[position])
    }
}

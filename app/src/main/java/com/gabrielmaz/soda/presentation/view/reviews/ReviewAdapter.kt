package com.gabrielmaz.soda.presentation.view.reviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gabrielmaz.soda.R
import com.gabrielmaz.soda.data.models.Review
import kotlinx.android.synthetic.main.item_review.view.*

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    var reviews = arrayListOf<Review>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]

        holder.position.text = (position + 1).toString()
        holder.author.text = review.author
        holder.content.text = review.content
    }


    inner class ReviewViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val position: TextView = mView.item_review_position
        val author: TextView = mView.item_review_author
        val content: TextView = mView.item_review_content
    }
}
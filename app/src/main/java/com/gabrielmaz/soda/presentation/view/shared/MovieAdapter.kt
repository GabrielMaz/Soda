package com.gabrielmaz.soda.presentation.view.shared

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.gabrielmaz.soda.R
import com.gabrielmaz.soda.data.controllers.RetrofitController
import com.gabrielmaz.soda.data.models.Movie
import kotlinx.android.synthetic.main.item_discover.view.*

class MovieAdapter(
    private val context: Context,
    private var onClick: (Movie) -> Unit
) : BaseAdapter() {

    var movies = listOf<Movie>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val item = movies[position]
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_discover, null)

        view.item_discover_title.text = item.title
        if (item.releaseDate.length > 4) {
            view.item_discover_year.text = item.releaseDate.subSequence(0, 4)
        }
        view.item_discover_rate.text = item.voteAverage.toString()
        view.item_discover_card.setOnClickListener {
            onClick(item)
        }

        Glide
            .with(context)
            .load("${RetrofitController.baseImageUrl}${item.posterPath}")
            .centerCrop()
            .placeholder(R.drawable.ic_place_holder)
            .into(view.item_discover_image)

        return view
    }

    override fun getItem(position: Int): Any {
        return movies[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return movies.size
    }

}
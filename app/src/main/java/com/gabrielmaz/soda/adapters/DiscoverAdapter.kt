package com.gabrielmaz.soda.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.gabrielmaz.soda.R
import com.gabrielmaz.soda.controllers.RetrofitController
import com.gabrielmaz.soda.models.Movie
import kotlinx.android.synthetic.main.item_discover.view.*

class DiscoverAdapter(
    private var discovers: ArrayList<Movie>,
    private val context: Context,
    private var onClick: (Movie) -> Unit
) :
    BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val item = discovers[position]

        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflator.inflate(R.layout.item_discover, null)

        view.item_discover_title.text = item.title
        view.item_discover_year.text = item.releaseDate.subSequence(0, 4)
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
        return discovers[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return discovers.size
    }

}
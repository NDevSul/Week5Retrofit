package com.uc.week4retrofit.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uc.week4retrofit.R
import com.uc.week4retrofit.helper.Const
import com.uc.week4retrofit.model.Result
import com.uc.week4retrofit.view.MovieDetail


class NowPlayingAdapter(private val dataSet: ArrayList<Result>) :
    RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView
        val tvReleased: TextView
        val imgnowplaying: ImageView
        val cvNowPlaying: CardView

        init {
            tvTitle = view.findViewById(R.id.tv_title_poster)
            tvReleased = view.findViewById(R.id.tv_released_poster)
            imgnowplaying = view.findViewById((R.id.img_now_playing))
            cvNowPlaying = view.findViewById((R.id.cv_now_playing))
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_now_playing, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.tvTitle.text = dataSet[position].title
        viewHolder.tvReleased.text = dataSet[position].release_date
        Glide.with(viewHolder.itemView).load(Const.IMG_URL + dataSet[position].poster_path).into(viewHolder.imgnowplaying)
        viewHolder.cvNowPlaying.setOnClickListener {
            val intent = Intent(it.context, MovieDetail::class.java)
            intent.putExtra("movie_id", dataSet[position].id)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount() = dataSet.size

}
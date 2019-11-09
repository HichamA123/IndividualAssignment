package com.example.individualassignment.model

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.individualassignment.R
import kotlinx.android.synthetic.main.item_game.view.*

class GameAdapter(private val games: List<Game>, private val onClick: (Game) -> Unit): RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    private lateinit var context: Context
    private var gameNum = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_game, parent, false)
        )
    }

    override fun getItemCount(): Int = games.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(games[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val maxLengthOfTitle = 17

        init {
            itemView.setOnClickListener {
                onClick(games[adapterPosition])
            }
        }
        fun bind(game: Game) {
            itemView.pbGame.visibility = View.VISIBLE
            Glide.with(context).load(game.background_image).listener(object: RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    itemView.pbGame.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    itemView.pbGame.visibility = View.GONE
                    return false
                }

            }).into(itemView.ivPoster)

            if (game.name.length > maxLengthOfTitle) itemView.tvTitle.text = game.name.substring(0, maxLengthOfTitle) + "..."
            else itemView.tvTitle.text = game.name

            itemView.tvReleaseDate.text = game.released.substring(0, 4)
            gameNum++
        }
    }
}
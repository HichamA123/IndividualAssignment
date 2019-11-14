package com.example.individualassignment.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.example.individualassignment.Functions
import com.example.individualassignment.R
import kotlinx.android.synthetic.main.item_game.view.*

class GameAdapter(private val games: List<Game>, private val onClick: (Game) -> Unit):
    RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    private lateinit var context: Context

    //select function
    var selectMode = MutableLiveData<Boolean>()
    var selectedHolders = arrayListOf<ViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_game, parent, false)
        )
    }

    override fun getItemCount(): Int = games.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = games[position]

        //itemView visibilty
        holder.itemView.pbGame.visibility = View.VISIBLE
        holder.itemView.checkSymbol.visibility = View.INVISIBLE
        holder.itemView.ivBlurryTop.setImageResource(0)
        holder.itemView.ivPoster.visibility = View.INVISIBLE

        //setting content
        if (game.name.length > holder.maxLengthOfTitle) holder.itemView.tvTitle.text = game.name.substring(0, holder.maxLengthOfTitle) + "..."
        else holder.itemView.tvTitle.text = game.name
        holder.itemView.tvReleaseDate.text = game.released.substring(0, 4)


        //setting image based on if game is selected
        if(game.selected) holder.setBlurImage(game)
        else holder.setPoster(game)


        holder.itemView.ivPoster.setOnLongClickListener {
            selectMode.value = true
            if(game.selected) deselectGame(game, holder, true)
            else selectGame(game, holder)
            true
        }

        holder.itemView.ivPoster.setOnClickListener {
            if(selectMode.value == true) {
                if(game.selected) deselectGame(game, holder, true)
                else selectGame(game, holder)
            } else {
                onClick(game)
            }

        }

    }

    private fun deselectGame(game: Game, holder: ViewHolder, removeHolder: Boolean) {
        println("DESELECT " + game.name)

        //usefull when calling from deselectAll method
        if(removeHolder) selectedHolders.remove(holder)

        //remove close button when no items are selected
        if(selectedHolders.size == 0) selectMode.value = false

        game.selected = false
        if(game.selected) {
            holder.setBlurImage(game)
        } else {
            //itemView visibilty
            holder.itemView.pbGame.visibility = View.VISIBLE
            holder.itemView.ivPoster.visibility = View.INVISIBLE
            holder.setPoster(game)
            holder.itemView.checkSymbol.visibility = View.INVISIBLE
            holder.itemView.ivBlurryTop.setImageResource(0)

        }
    }

    private fun selectGame(game: Game, holder: ViewHolder ) {
        println("SELECT " + game.name )
        selectedHolders.add(holder)
        game.selected = true
        if(game.selected) holder.setBlurImage(game)
        else holder.setPoster(game)
    }

    fun deselectAll() {
        selectMode.value = false

        games.forEach{ game ->
            if (game.selected) {
                selectedHolders.forEach { holder ->
                    if(holder.itemView.tvTitle.text.substring(0,5) == game.name.substring(0,5)) {
                        deselectGame(game, holder, false)
                    }
                }
                game.selected = false
            }
        }

        selectedHolders.clear()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val maxLengthOfTitle = 17

        fun setPoster(game: Game) {
            Glide.with(context).load(game.background_image).listener(object: RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    //itemView visibilty
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
                    //itemView visibilty
                    itemView.ivPoster.visibility = View.VISIBLE
                    itemView.pbGame.visibility = View.GONE
                    return false
                }

            }).into(itemView.ivPoster)
        }

        fun setBlurImage(game: Game) {
            Glide.with(context)
                .asBitmap()
                .load(game.background_image)
                .into(object : CustomTarget<Bitmap>(1980, 1080) {
                    override fun onResourceReady(resource: Bitmap, transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?) {
                        val blurredBitmap = Functions().blur(resource, context, true)
                        itemView.ivBlurryTop.setImageBitmap(blurredBitmap)
                        //itemView visibilty
                        itemView.checkSymbol.visibility = View.VISIBLE
                        itemView.pbGame.visibility = View.GONE
                        itemView.ivPoster.visibility = View.VISIBLE
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {
                        // this is called when imageView is cleared on lifecycle call or for
                        // some other reason.
                        // if you are referencing the bitmap somewhere else too other than this imageView
                        // clear it here as you can no longer have the bitmap
                    }
                })
        }

    }
}
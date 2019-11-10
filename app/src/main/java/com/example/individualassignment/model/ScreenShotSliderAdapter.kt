package com.example.individualassignment.model

import com.example.individualassignment.R
import android.content.Context
import com.smarteist.autoimageslider.SliderViewAdapter
import com.bumptech.glide.Glide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.item_screenshot.view.*


class ScreenShotSliderAdapter(private val screenShots: List<Screenshot>) : SliderViewAdapter<ScreenShotSliderAdapter.SliderAdapterVH>() {

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.item_screenshot, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) = viewHolder.bind(screenShots[position].image)

    override fun getCount(): Int = screenShots.size

    inner class SliderAdapterVH(var itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
        var imageViewBackground: ImageView

        init {
            imageViewBackground = itemView.ivScreenShot
        }

       fun bind(screenshot: String) {
                Glide.with(itemView).load(screenshot).into(imageViewBackground)
       }
    }
}
package com.example.individualassignment.fragments


import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.individualassignment.R
import kotlinx.android.synthetic.main.fragment_game_detail.*


/**
 * A simple [Fragment] subclass.
 */
class GameDetailFragment : Fragment() {

    private val args: GameDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this)
            .asBitmap()
            .load(args.game.background_image)
            .into(object : CustomTarget<Bitmap>(1980, 1080){
                override fun onResourceReady(resource: Bitmap, transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?) {
                    val blurredBitmap = blur(resource)
                    ivBackground.setImageBitmap(blurredBitmap)
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                    // this is called when imageView is cleared on lifecycle call or for
                    // some other reason.
                    // if you are referencing the bitmap somewhere else too other than this imageView
                    // clear it here as you can no longer have the bitmap
                }
            })

        Glide.with(this).load(args.game.background_image).into(ivPoster)

//        tvDescription.text =


    }

    private val BITMAP_SCALE = 1.2f
    private val BLUR_RADIUS = 18.5f

    fun blur(image: Bitmap): Bitmap {
        val width = Math.round(image.width * BITMAP_SCALE)
        val height = Math.round(image.height * BITMAP_SCALE)

        val inputBitmap = Bitmap.createScaledBitmap(image, width, height, false)
        val outputBitmap = Bitmap.createBitmap(inputBitmap)

        val rs = RenderScript.create(context)
        val theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
        val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)
        theIntrinsic.setRadius(BLUR_RADIUS)
        theIntrinsic.setInput(tmpIn)
        theIntrinsic.forEach(tmpOut)
        tmpOut.copyTo(outputBitmap)

        return outputBitmap
    }


}

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
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.example.individualassignment.R
import com.example.individualassignment.fragments.viewmodels.UGFViewModel
import com.example.individualassignment.model.Game
import kotlinx.android.synthetic.main.content_game_detail.*
import kotlinx.android.synthetic.main.fragment_game_detail.*


/**
 * A simple [Fragment] subclass.
 */
class GameDetailFragment : Fragment() {

    private val args: GameDetailFragmentArgs by navArgs()
    private val BITMAP_SCALE = 1.2f
    private val BLUR_RADIUS = 18.5f
    private lateinit var viewModel: UGFViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.initView()
    }

    private fun initView() {
        pbGame.visibility = View.VISIBLE

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

        Glide.with(this).load(args.game.background_image).listener(object: RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                pbGame.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                pbGame.visibility = View.GONE
                return false
            }

        }).into(ivPoster)

        initViewModels()
    }

    private fun initViewModels() {
        viewModel = ViewModelProviders.of(this).get(UGFViewModel::class.java)

        viewModel.getGameDetails(args.game.id)

        viewModel.detailedGame.observe(this, Observer {
            this.setGameContent(it)
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun setGameContent(game: Game) {
        tvDescription.text = game.description_raw
        tvTitle.text = game.name
        tvReleaseDate.text = getString(R.string.game_detail_game_release_date, game.released)

        tvRating.text = getString(R.string.game_detail_game_rating, game.rating)
        //todo fix ratingbar
//        ratingBar.rating = game.rating.toFloat()
        //todo setplatforms, set screenshots

    }

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

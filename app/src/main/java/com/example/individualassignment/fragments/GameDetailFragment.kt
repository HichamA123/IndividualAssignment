package com.example.individualassignment.fragments


import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
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
import com.example.individualassignment.Functions
import com.example.individualassignment.R
import com.example.individualassignment.fragments.viewmodels.UGFViewModel
import com.example.individualassignment.model.ScreenShotSliderAdapter
import com.example.individualassignment.model.Screenshot
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import kotlinx.android.synthetic.main.content_game_detail.*
import kotlinx.android.synthetic.main.fragment_game_detail.*


/**
 * A simple [Fragment] subclass.
 */
class GameDetailFragment : Fragment() {

    private val args: GameDetailFragmentArgs by navArgs()
    private lateinit var viewModel: UGFViewModel
    private val screenshots = arrayListOf<Screenshot>()
    private val screenshotSliderAdapter = ScreenShotSliderAdapter(screenshots)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        imageSlider.sliderAdapter = screenshotSliderAdapter
        imageSlider.startAutoCycle()
        imageSlider.setIndicatorAnimation(IndicatorAnimations.WORM)
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)


        setPoster()
        setOtherGameContent()
        initViewModels()
    }

    private fun initViewModels() {
        viewModel = ViewModelProviders.of(this).get(UGFViewModel::class.java)

        //needed for description of game
        viewModel.getGameDetails(args.game.id)
        viewModel.detailedGame.observe(this, Observer {
            tvDescription.text = it.description_raw
        })

        //screenshots
        viewModel.getScreenShots(args.game.id)
        viewModel.screenshots.observe(this, Observer {
            screenshots.clear()
            screenshots.addAll(it)
            screenshotSliderAdapter.notifyDataSetChanged()

        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun setOtherGameContent() {
        tvTitle.text = args.game.name
        tvReleaseDate.text = getString(R.string.game_detail_game_release_date, args.game.released)
        tvRating.text = args.game.rating.toFloat().toString()
        ratingBar.rating = args.game.rating.toFloat()

        for (platform in args.game.platforms) hasPlatform(platform.platform?.name)
    }

    private fun hasPlatform(name: String?) {
        when(name) {
            "PlayStation 4" -> hasPlaystation()
            "PlayStation" -> hasPlaystation()
            "PC" -> hasWindows()
            "Xbox One" -> hasXbox()
            "Xbox" -> hasXbox()
            "Nintendo Switch" -> hasNintendo()
            "Nintendo" -> hasNintendo()
        }

    }

    private fun hasWindows() {
        tvWindows.setTextColor(Color.WHITE)
        ivWindows.setBackgroundResource(R.drawable.ic_windows)
    }

    private fun hasPlaystation() {
        tvPlaystation.setTextColor(Color.WHITE)
        ivPlaystation.setBackgroundResource(R.drawable.ic_playstation)
    }

    private fun hasNintendo() {
        tvSwitch.setTextColor(Color.WHITE)
        ivSwitch.setBackgroundResource(R.drawable.ic_switch)
    }

    private fun hasXbox() {
        tvXbox.setTextColor(Color.WHITE)
        ivXbox.setBackgroundResource(R.drawable.ic_xbox)
    }

    private fun setPoster() {
        pbGame.visibility = View.VISIBLE
        Glide.with(this)
            .asBitmap()
            .load(args.game.background_image)
            .into(object : CustomTarget<Bitmap>(1980, 1080) {
                override fun onResourceReady(resource: Bitmap, transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?) {
                    val blurredBitmap = Functions().blur(resource, context, false)
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
    }

}

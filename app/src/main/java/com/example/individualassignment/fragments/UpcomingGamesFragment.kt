package com.example.individualassignment.fragments


import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.example.individualassignment.Functions
import com.example.individualassignment.R
import com.example.individualassignment.fragments.viewmodels.UGFViewModel
import com.example.individualassignment.model.Game
import com.example.individualassignment.model.GameAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_upcoming_games.*
import kotlinx.android.synthetic.main.item_game.view.*


const val GAME = "GAME"

/**
 * A simple [Fragment] subclass.
 */
class UpcomingGamesFragment : Fragment() {

    private val games = arrayListOf<Game>()
    private val gamesAdapter = GameAdapter(games) {game ->
        startDetailActivity(game)
    }

    private lateinit var viewModel: UGFViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModels()
    }

    private fun initView() {
        rvUpcomingGames.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        rvUpcomingGames.adapter = gamesAdapter
    }

    private fun initViewModels() {
        viewModel = ViewModelProviders.of(this).get(UGFViewModel::class.java)

        viewModel.getGames("2019-10-10,2020-10-10", "-added")

        viewModel.games.observe(this, Observer {
            games.clear()
            games.addAll(it)
            gamesAdapter.notifyDataSetChanged()
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_deselect -> {
                Snackbar.make(view!!, "Cleared selected games", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                gamesAdapter.deselectAll()
                true
            }
            R.id.action_search -> {
                false
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        gamesAdapter.selectMode.observe(this, Observer {
            val deselect = menu.findItem(R.id.action_deselect)
            val search = menu.findItem(R.id.action_search)

            if (it) {
                deselect.setVisible(true)
                search.setVisible(false)
            } else {
                deselect.setVisible(false)
                search.setVisible(true)
            }

            super.onCreateOptionsMenu(menu, inflater)
        })
    }

    private fun startDetailActivity(game: Game) {
        val action = UpcomingGamesFragmentDirections.actionNavUpcomingGamesToGameDetailFragment(game)
        findNavController().navigate(action)
    }

}

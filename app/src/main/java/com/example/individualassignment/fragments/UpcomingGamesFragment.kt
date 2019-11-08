package com.example.individualassignment.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.individualassignment.R
import com.example.individualassignment.fragments.viewmodels.UGFViewModel
import com.example.individualassignment.model.Game
import com.example.individualassignment.model.GameAdapter
import kotlinx.android.synthetic.main.fragment_upcoming_games.*


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

    private fun startDetailActivity(game: Game) {
        val action = UpcomingGamesFragmentDirections.actionNavUpcomingGamesToGameDetailFragment(game)
        findNavController().navigate(action)
    }

}

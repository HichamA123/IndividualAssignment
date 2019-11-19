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
import com.example.individualassignment.Functions
import com.example.individualassignment.R
import com.example.individualassignment.model.Game
import com.example.individualassignment.model.GameAdapter
import kotlinx.android.synthetic.main.fragment_buy_list.*
import kotlinx.android.synthetic.main.fragment_upcoming_games.*

/**
 * A simple [Fragment] subclass.
 */
class BuyListFragment : Fragment() {

    private val games = arrayListOf<Game>()
    private val gamesAdapter = GameAdapter(games) {game ->
        startDetailActivity(game)
    }

    private lateinit var viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buy_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModels()
    }

    private fun initView() {
        rvBuyList.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        rvBuyList.adapter = gamesAdapter
    }

    private fun initViewModels() {
        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
//
//        viewModel.getGames(dates[0] + "," + dates[1], "-added")
//
//        viewModel.games.observe(this, Observer {
//            games.clear()
//            games.addAll(it)
//            gamesAdapter.notifyDataSetChanged()
//        })

//        viewModel.error.observe(this, Observer {
//            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
//        })
    }


    private fun startDetailActivity(game: Game) {
        val action = BuyListFragmentDirections.actionNavBuyListToGameDetailFragment(game)
        findNavController().navigate(action)
    }


}

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
import com.example.individualassignment.gameRepository
import com.example.individualassignment.mainScope
import com.example.individualassignment.model.Game
import com.example.individualassignment.model.GameAdapter
import kotlinx.android.synthetic.main.fragment_buy_list.*
import kotlinx.android.synthetic.main.fragment_upcoming_games.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    }

    private fun initView() {
        rvBuyList.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        gamesAdapter.adapterIsSelectable = false
        rvBuyList.adapter = gamesAdapter
        getGamesFromDatabase()
    }

    private fun getGamesFromDatabase() {
        mainScope.launch {
            val games = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }

            this@BuyListFragment.games.clear()
            this@BuyListFragment.games.addAll(games)
            gamesAdapter.notifyDataSetChanged()
        }
    }



    private fun startDetailActivity(game: Game) {
        val action = BuyListFragmentDirections.actionNavBuyListToGameDetailFragment(game)
        findNavController().navigate(action)
    }


}

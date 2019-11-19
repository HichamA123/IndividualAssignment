package com.example.individualassignment.fragments


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.individualassignment.Functions
import com.example.individualassignment.R
import com.example.individualassignment.model.Game
import com.example.individualassignment.model.GameAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_popular_games.*


/**
 * A simple [Fragment] subclass.
 */
class PopularGamesFragment : Fragment() {

    private val games = arrayListOf<Game>()
    private val gamesAdapter = GameAdapter(games) {game ->
        startDetailActivity(game)
    }

    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFab(view)

        initView()
        initViewModels()
    }

    private fun setFab(view: View) {
        val fab: FloatingActionButton = view.findViewById(R.id.save)
        fab.setOnClickListener {

            if (gamesAdapter.selectMode.value == true) {
                for (game in gamesAdapter.games) {
                    if (game.selected) {
                        //TODO save games
                        println(game.name)
                    }
                }
                gamesAdapter.deselectAll()
                Snackbar.make(view, "Saved selected game(s).", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(view, "Please select some games you want to save.", Snackbar.LENGTH_LONG).show()
            }

        }
    }

    private fun initView() {
        rvPopularGames.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        rvPopularGames.adapter = gamesAdapter
    }

    private fun initViewModels() {
        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)

        val dates = Functions().getLastYear()

        viewModel.getGames(dates[0] + "," + dates[1], "-added")

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
                Snackbar.make(view!!, "Cleared selected games.", Snackbar.LENGTH_LONG).show()
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
        val action = PopularGamesFragmentDirections.actionNavPopularGamesToGameDetailFragment(game)
        findNavController().navigate(action)
    }


}

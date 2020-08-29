package hoannt.android.moviedb.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.AndroidInjection
import hoannt.android.moviedb.R
import hoannt.android.moviedb.ui.adapter.MovieListAdapter
import hoannt.android.moviedb.ui.viewmodel.MovieListViewmodel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private val TAG = "MainActivity_MinhLam"
    private lateinit var movieViewModel: MovieListViewmodel
    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        movieListAdapter = MovieListAdapter(emptyList())
        recyclerView.adapter = movieListAdapter
        movieViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MovieListViewmodel::class.java)
        movieViewModel.loadMoreMovie()
        movieViewModel.getMovieLiveData().observe(this, Observer {
            Log.i(TAG, "onCreate: ${it.data?.get(0)}")
            if (!it.data.isNullOrEmpty()) {
                Log.i(TAG, "onCreate: Go to it.data not null")
                movieListAdapter.setList(it.data)
            }
        })
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        })
    }
}
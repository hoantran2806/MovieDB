package hoannt.android.moviedb.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.AndroidSupportInjection
import hoannt.android.moviedb.R
import hoannt.android.moviedb.ui.adapter.MovieListAdapter
import hoannt.android.moviedb.ui.adapter.RecyclerViewPaginator
import hoannt.android.moviedb.ui.viewmodel.MovieListViewmodel
import hoannt.android.moviedb.ui.viewmodel.ShareMovieViewModel
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject


class MovieListFragment : Fragment(), MovieListAdapter.RecyclerViewItemClick {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var movielistViewModel: MovieListViewmodel
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var selectedMovieViewModel: ShareMovieViewModel
    private val TAG = "MovieListFrag_MinhLam"
//    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)

        movieListAdapter = MovieListAdapter(mutableListOf(), this)
        movielistViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MovieListViewmodel::class.java)
//        Log.i(TAG, "onCreate: call movielistViewModel.loadMoreMovie()")
//        movielistViewModel.loadMoreMovie()
        movielistViewModel.getMovieLiveData().observe(this, Observer {
            if (it.data != null && !it.data.isEmpty()) {
                Log.i(
                    TAG,
                    "onCreate: it.isEmpty = ${it.data.isNullOrEmpty()} and size = ${it.data.size}"
                )
                movieListAdapter.setList(it.data)
                movieListAdapter.notifyDataSetChanged()
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_movie_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedMovieViewModel =
            ViewModelProvider(requireActivity()).get(ShareMovieViewModel::class.java)
        initAdapter()

//        movielistViewModel.getMovieLiveData().observe(this, Observer {
//            if(it.isSuccess){
//                Log.i(TAG, "onViewCreated: it.isEmpty = ${it.data.isNullOrEmpty()} and size = ${it.data?.size}")
//                movieListAdapter.setList(it.data!!)
////                recycler_list_movie.adapter = movieListAdapter
//                movieListAdapter.notifyDataSetChanged()
//            }
//        })

    }

    override fun onItemSelected(position: Int) {
        selectedMovieViewModel.select(movieListAdapter.getItem(position))
        findNavController().navigate(R.id.movieDetailFragment)
    }

    private fun initAdapter() {
        recycler_list_movie.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        recycler_list_movie.adapter = movieListAdapter
        Log.i(TAG, "onViewCreated: movielistViewModel.getMovieLiveData()")
        recycler_list_movie.addOnScrollListener(object :
            RecyclerViewPaginator(recycler_list_movie) {
            override val isLastPage: Boolean
                get() = movielistViewModel.isLastPage()

            override fun loadFirst(page: Long) {
                movielistViewModel.loadMoreMovie(page)
            }

            override fun loadMore(page: Long) {
                movielistViewModel.loadMoreMovie(page)
            }

        })
    }

}
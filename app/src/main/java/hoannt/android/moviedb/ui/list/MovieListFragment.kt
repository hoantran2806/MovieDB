package hoannt.android.moviedb.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.AndroidSupportInjection
import hoannt.android.moviedb.R
import hoannt.android.moviedb.data.local.entity.MovieEntity
import hoannt.android.moviedb.ui.base.customview.PagerSnapHelper
import hoannt.android.moviedb.ui.list.adapter.MovieListAdapter
import hoannt.android.moviedb.ui.list.adapter.RecyclerViewPaginator
import hoannt.android.moviedb.ui.list.viewmodel.MovieListViewmodel
import hoannt.android.moviedb.ui.list.viewmodel.ShareMovieViewModel
import kotlinx.android.synthetic.main.fragment_movie_list.view.*
import kotlinx.android.synthetic.main.movie_item_layout.*
import javax.inject.Inject


class MovieListFragment : Fragment(), MovieListAdapter.RecyclerViewItemClick {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var movielistViewModel: MovieListViewmodel
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var selectedMovieViewModel: ShareMovieViewModel
    private var newView: View? = null
    private val TAG = "MovieListFrag_MinhLam"
    private var currentPage: Long = 1L
//    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        setHasOptionsMenu(true)
        movieListAdapter = MovieListAdapter(mutableListOf(), this)
        movielistViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MovieListViewmodel::class.java)

        movielistViewModel.getMovieLiveData().observe(this, Observer {
            if (it.isLoading) {

            } else if (it.data != null && !it.data.isEmpty()) {
                isLoaded = true
                Log.i(
                    TAG,
                    "onCreate: it.isEmpty = ${it.data.isNullOrEmpty()} and size = ${it.data.size}"
                )
                movieListAdapter.setList(it.data)
//                movieListAdapter.notifyDataSetChanged()
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (newView == null) {
            newView = inflater.inflate(R.layout.fragment_movie_list, container, false)
            initAdapter(this.newView!!)
            selectedMovieViewModel =
                ViewModelProvider(requireActivity()).get(ShareMovieViewModel::class.java)
        }
        return newView
    }

    override fun onItemSelected(movieEntity: MovieEntity, imageView: ImageView) {
        movielistViewModel.onViewModelStop()
        val extras = FragmentNavigatorExtras(movie_image to movieEntity.posterPath!!)
        selectedMovieViewModel.select(movieEntity)

        val action =
            MovieListFragmentDirections.navToItemDetailFragment(
                uri = movieEntity.posterPath!!
            )
        findNavController().navigate(action, extras)
    }

    var isLoaded = true
    private fun initAdapter(view: View) {
        view.recycler_list_movie.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        view.recycler_list_movie.adapter = movieListAdapter
        Log.i(TAG, "onViewCreated: movielistViewModel.getMovieLiveData()")

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(view.recycler_list_movie)
        view.recycler_list_movie.addOnScrollListener(object :
            RecyclerViewPaginator(view.recycler_list_movie) {
            override val isLastPage: Boolean
                get() = movielistViewModel.isLastPage()

            override fun loadFirst() {
                Log.i(TAG, "loadFirst: page = $currentPage")
                if (isLoaded && currentPage == 1L) {
                    isLoaded = false
                    movielistViewModel.loadMoreMovie(currentPage)
                }
            }

            override fun loadMore() {
                Log.i(TAG, "loadMore: page = $currentPage")
                if (isLoaded) {
                    isLoaded = false
                    movielistViewModel.loadMoreMovie(++currentPage)
                }
            }

        })
    }

}
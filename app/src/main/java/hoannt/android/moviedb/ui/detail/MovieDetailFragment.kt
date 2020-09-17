package hoannt.android.moviedb.ui.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import dagger.android.support.AndroidSupportInjection
import hoannt.android.moviedb.AppConstants
import hoannt.android.moviedb.R
import hoannt.android.moviedb.data.local.entity.MovieEntity
import hoannt.android.moviedb.data.network.model.credit.Cast
import hoannt.android.moviedb.data.network.model.credit.Crew
import hoannt.android.moviedb.ui.detail.adapter.CastListAdapter
import hoannt.android.moviedb.ui.detail.adapter.CrewListAdapter
import hoannt.android.moviedb.ui.detail.adapter.SimilarMovieListAdapter
import hoannt.android.moviedb.ui.detail.viewmodel.MovieDetailViewModel
import hoannt.android.moviedb.ui.list.viewmodel.ShareMovieViewModel
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.layout_list_cast_end_crew.view.*
import javax.inject.Inject


class MovieDetailFragment : Fragment(), SimilarMovieListAdapter.OnClickDetailSimilarMovie {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var imageUri: String
    private lateinit var detailViewModel: MovieDetailViewModel
    private lateinit var castListAdapter: CastListAdapter
    private lateinit var crewListAdapter: CrewListAdapter
    private lateinit var similarMovieListAdapter: SimilarMovieListAdapter
    private lateinit var movieEntity: MovieEntity
    private lateinit var sharedMovieViewModel: ShareMovieViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movie_detail, container, false)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
//        (activity as MainActivity).toolbar.visibility = View.INVISIBLE
//        castListAdapter = CastListAdapter(mutableListOf())
//        crewListAdapter = CrewListAdapter(mutableListOf())
//        similarMovieListAdapter = SimilarMovieListAdapter(mutableListOf())
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        detailViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel::class.java)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack(R.id.destination_movie, false)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        sharedMovieViewModel =
            ViewModelProvider(requireActivity()).get(ShareMovieViewModel::class.java)
        movieEntity = sharedMovieViewModel.selected.value!!
        detailViewModel.fetchMoreDetail(movieEntity)
//        detailViewModel.getDetailMovie().observe(this, Observer { movieEntity ->
//            if (movieEntity != null) {
//                if (!movieEntity.cast.isNullOrEmpty()) {
//                    castListAdapter.setList(movieEntity.cast!!)
//                }
//
//                if (!movieEntity.crew.isNullOrEmpty()) {
//                    crewListAdapter.setList(movieEntity.crew!!)
//                }
//
//                if (!movieEntity.similarMovie.isNullOrEmpty()) {
//                    similarMovieListAdapter.setList(movieEntity.similarMovie!!)
//                }
//            }
//        })
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        var sharedMovieViewModel =
//            ViewModelProvider(requireActivity()).get(ShareMovieViewModel::class.java)
//        var movieEntity = sharedMovieViewModel.selected.value
        imageUri = requireArguments().getString("uri").toString()
//        var movie = arguments?.getParcelable<MovieEntity>(AppConstants.SIMILAR_DETAIL)
        movie_image.apply {
            transitionName = imageUri
            Picasso.get().load(AppConstants.IMAGE_URL + imageUri).into(movie_image)
        }
        movie_title.text = movieEntity.title
        movie_overview.text = movieEntity.overview
        movie_release.text = movieEntity.releaseDate
        view.list_cast_movie.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL, false
        )
        view.list_crew_movie.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL, false
        )
        view.list_similar_movie.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL, false
        )
//        setUpCastList(view)
//        setUpCrewList(view)
//        setUpSimilarMoviesList(view)
//        detailViewModel.fetchMoreDetail(movieEntity!!)
//        detailViewModel.getDetailMovie().observe(viewLifecycleOwner, Observer {movieEntity ->
//            if (movieEntity != null) {
//                movieEntity.cast?.let { it1 ->
//                    castListAdapter.setList(it1) }
//
//                movieEntity.crew?.let {
//                    crewListAdapter.setList(it)
//                }
//
//                movieEntity.similarMovie?.let {
//                    similarMovieListAdapter.setList(it)
//                }
//            }
//        })

        detailViewModel.getDetailMovie().observe(viewLifecycleOwner, Observer { movieEntity ->
            if (movieEntity != null) {
                movie_image.apply {
                    transitionName = movieEntity.posterPath
                    Picasso.get().load(AppConstants.IMAGE_URL + movieEntity.posterPath)
                        .into(movie_image)
                }
                if (!movieEntity.cast.isNullOrEmpty()) {
//                    castListAdapter.setList(movieEntity.cast!!)
                    setUpCastList(view, movieEntity.cast!!)
                }

                if (!movieEntity.crew.isNullOrEmpty()) {
//                    crewListAdapter.setList(movieEntity.crew!!)
                    setUpCrewList(view, movieEntity.crew!!)
                }

                if (!movieEntity.similarMovie.isNullOrEmpty()) {
//                    similarMovieListAdapter.setList(movieEntity.similarMovie!!)
                    setUpSimilarMoviesList(view, movieEntity.similarMovie!!)
                }
            }
        })
    }

    private fun setUpCastList(view: View, list: List<Cast>) {
        castListAdapter = CastListAdapter(list)
        view.list_cast_movie.adapter = castListAdapter
//        (view.list_cast_movie.adapter as CastListAdapter).notifyDataSetChanged()
    }

    private fun setUpCrewList(view: View, list: List<Crew>) {
        crewListAdapter = CrewListAdapter(list.toMutableList())
        view.list_crew_movie.adapter = crewListAdapter
    }

    private fun setUpSimilarMoviesList(view: View, list: List<MovieEntity>) {
        similarMovieListAdapter = SimilarMovieListAdapter(list.toMutableList(), this)
        view.list_similar_movie.adapter = similarMovieListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        detailViewModel.onViewModelStop()
    }

    override fun onDetailClick(position: Int) {
        val movieEntity = similarMovieListAdapter.getSimilarMovieDetail(position)
        val extras = FragmentNavigatorExtras(movie_image to movieEntity.posterPath!!)
        sharedMovieViewModel.select(movieEntity)
        findNavController().navigate(R.id.movieDetailFragment)

    }
}
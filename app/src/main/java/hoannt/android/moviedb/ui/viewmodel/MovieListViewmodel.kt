package hoannt.android.moviedb.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hoannt.android.moviedb.data.ApiServices
import hoannt.android.moviedb.data.Resource
import hoannt.android.moviedb.data.local.dao.MovieDAO
import hoannt.android.moviedb.data.local.entity.MovieEntity
import hoannt.android.moviedb.data.local.repository.MovieRepository
import javax.inject.Inject

class MovieListViewmodel @Inject constructor(
    movieDAO: MovieDAO,
    apiServices: ApiServices
) : ViewModel() {
    private val TAG = "movieViewModel_Minhlam"
    private val movieRepository = MovieRepository(movieDAO, apiServices)

    private val movieListLiveData = MutableLiveData<Resource<List<MovieEntity>>>()

    fun loadMoreMovie() {
        movieRepository.loadMovieByType().subscribe { resource ->
            Log.i(TAG, "loadMoreMovie: ${resource.data?.get(0)}")
            getMovieLiveData().postValue(resource)
        }
    }

    fun getMovieLiveData() = movieListLiveData
}
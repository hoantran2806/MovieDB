package hoannt.android.moviedb.ui.list.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import hoannt.android.moviedb.data.ApiServices
import hoannt.android.moviedb.data.Resource
import hoannt.android.moviedb.data.local.dao.MovieDAO
import hoannt.android.moviedb.data.local.entity.MovieEntity
import hoannt.android.moviedb.data.local.repository.MovieRepository
import hoannt.android.moviedb.ui.base.BaseViewModel
import javax.inject.Inject

class MovieListViewmodel @Inject constructor(
    movieDAO: MovieDAO,
    apiServices: ApiServices
) : BaseViewModel() {
    private val TAG = "movieViewModel_MinhLam"
    private val movieRepository = MovieRepository(movieDAO, apiServices)

    private val movieListLiveData = MutableLiveData<Resource<List<MovieEntity>>>()

    fun loadMoreMovie(page: Long) {
        movieRepository.loadMovieByType(page)
            .doOnSubscribe {
                addToDisposable(it)
            }
            .subscribe { resource ->
                Log.i(TAG, "loadMoreMovie: ${resource.data?.get(0)}")
                getMovieLiveData().postValue(resource)
            }
    }

    fun isLastPage(): Boolean {
        if (movieListLiveData.value != null) {
            if (movieListLiveData.value?.data != null &&
                !movieListLiveData.value?.data?.isEmpty()!!
            ) {
                return movieListLiveData.value?.data?.get(0)?.isLastPage()!!
            }
        }
        return true
    }

    fun getMovieLiveData() = movieListLiveData
}
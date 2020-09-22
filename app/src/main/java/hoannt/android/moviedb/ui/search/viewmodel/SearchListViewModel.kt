package hoannt.android.moviedb.ui.search.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import hoannt.android.moviedb.data.ApiServices
import hoannt.android.moviedb.data.Resource
import hoannt.android.moviedb.data.local.dao.MovieDAO
import hoannt.android.moviedb.data.local.entity.MovieEntity
import hoannt.android.moviedb.data.local.repository.MovieRepository
import hoannt.android.moviedb.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchListViewModel @Inject constructor(
    apiServices: ApiServices,
    movieDAO: MovieDAO
) : BaseViewModel() {

    private val TAG = "SearchViewModel_MinhLam"
    private val movieRepository = MovieRepository(movieDAO, apiServices)
    private val searchLiveData = MutableLiveData<Resource<List<MovieEntity>>>()

    fun search(query: String, page: Long) {
        var dis = movieRepository.searchMovieAndTv(query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                Log.i(
                    TAG,
                    "SearchListViewModel search:  resource.isSuccess = ${resource.isSuccess}"
                )
                getSearchResult().postValue(resource)
            }

        addToDisposable(dis)

    }

    fun isLastPage(): Boolean {
        if (searchLiveData.value != null) {
            if (!searchLiveData.value!!.data.isNullOrEmpty()) {
                return searchLiveData.value!!.data?.get(0)?.isLastPage()!!
            }
        }
        return true
    }

    fun getSearchResult() = searchLiveData

}
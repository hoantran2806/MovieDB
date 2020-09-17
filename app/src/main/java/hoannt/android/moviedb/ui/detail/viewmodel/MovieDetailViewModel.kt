package hoannt.android.moviedb.ui.detail.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import hoannt.android.moviedb.data.ApiServices
import hoannt.android.moviedb.data.local.dao.MovieDAO
import hoannt.android.moviedb.data.local.entity.MovieEntity
import hoannt.android.moviedb.data.local.repository.MovieRepository
import hoannt.android.moviedb.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    movieDAO: MovieDAO,
    movieApiServices: ApiServices
) : BaseViewModel() {
    private val TAG = "DetailViewModel_MinhLam"
    private val movieRepository = MovieRepository(movieDAO, movieApiServices)
    private val movieDetailLivaData = MutableLiveData<MovieEntity>()

    fun getDetailMovie() = movieDetailLivaData

    @SuppressLint("CheckResult")
    fun fetchMoreDetail(movieEntity: MovieEntity) {
        movieRepository.loadMovieDetail(movieEntity.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                addToDisposable(it)
            }
            .subscribe({ resouce ->
                if (resouce.isLoaded) {
                    Log.i(TAG, "fetchMoreDetail: resouce.isLoaded = true")
                    movieDetailLivaData.postValue(resouce.data)
                }
                if (resouce.isSuccess) {
                    Log.i(TAG, "fetchMoreDetail: resouce.isSuccess = true")
                }
                if (resouce.isLoading) {
                    Log.i(TAG, "fetchMoreDetail: resouce.isLoading = true")
                }
                if (resouce.isError) {
                    Log.i(TAG, "fetchMoreDetail: resouce.error = true")
                }
            }, { ex ->
                Log.i(TAG, "fetchMoreDetail: Throw exception: ${ex.message}")
            })
    }
}
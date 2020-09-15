package hoannt.android.moviedb.ui.detail.viewmodel

import android.annotation.SuppressLint
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
            .subscribe { resouce ->
                if (resouce.isLoaded) {
                    movieDetailLivaData.postValue(resouce.data)
                }
            }
    }
}
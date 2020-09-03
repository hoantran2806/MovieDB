package hoannt.android.moviedb.data.local.repository

import android.util.Log
import hoannt.android.moviedb.data.ApiServices
import hoannt.android.moviedb.data.NetworkBoundResource
import hoannt.android.moviedb.data.Resource
import hoannt.android.moviedb.data.local.dao.MovieDAO
import hoannt.android.moviedb.data.local.entity.MovieEntity
import hoannt.android.moviedb.data.network.model.MovieResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieDAO: MovieDAO,
    private val movieApiServices: ApiServices
) {
    private val TAG = "MovieRepository_MinhLam"
    fun loadMovieByType(page: Long): Observable<Resource<List<MovieEntity>>> {
        return object : NetworkBoundResource<List<MovieEntity>, MovieResponse>() {
            override fun saveCallResult(item: MovieResponse) {
                Log.i(TAG, "Call method saveCallResult: ")
                for (entity: MovieEntity in item.results) {
                    entity.page = item.page.toLong()
                    entity.totalPages = item.totalPages.toLong()
                }

                val result = movieDAO.insertMovies(item.results)
                Log.i(TAG, "saveCallResult: ${result.size}")
            }

            override fun shouldFetch(): Boolean {
                return true
            }

            override fun loadFromDb(): Flowable<List<MovieEntity>> {
                Log.i(TAG, "Call method loadFromDb: ")
                val movieEntityList = movieDAO.getMovieByPage(page)
                return if (movieEntityList == null || movieEntityList.isEmpty()) {
                    Flowable.empty()
                } else {
                    Log.i(
                        TAG,
                        "loadFromDb: Flowable.just(movieEntityList) size = ${movieEntityList.size}"
                    )
                    Flowable.just(movieEntityList)
                }
            }

            override fun createCall(): Observable<Resource<MovieResponse>> {
                Log.i(TAG, "createCall: Call method createCall() in MovieRepository")
                return movieApiServices.getMoviesPopular(page = page)
                    .flatMap { movieResponse ->
                        Observable.just(
                            if (movieResponse == null) Resource.error(
                                "Error",
                                MovieResponse(1, emptyList(), 0, 1)
                            )
                            else Resource.success(movieResponse)
                        )
                    }
            }
        }.getAsObservable()
    }
}
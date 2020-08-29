package hoannt.android.moviedb.data.local.repository

import hoannt.android.moviedb.data.ApiServices
import hoannt.android.moviedb.data.NetworkBoundResource
import hoannt.android.moviedb.data.Resource
import hoannt.android.moviedb.data.local.dao.MovieDAO
import hoannt.android.moviedb.data.local.entity.MovieEntity
import hoannt.android.moviedb.data.network.model.MovieResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class MovieRepository(
    private val movieDAO: MovieDAO,
    private val movieApiServices: ApiServices
) {

    fun loadMovieByType(): Observable<Resource<List<MovieEntity>>> {
        return object : NetworkBoundResource<List<MovieEntity>, MovieResponse>() {
            override fun saveCallResult(item: MovieResponse) {
                for (entity: MovieEntity in item.results) {
                    entity.page = item.page.toLong()
                    entity.totalPages = item.totalPages.toLong()
                }
                movieDAO.insertMovies(item.results)
            }

            override fun shouldFetch(): Boolean {
                return true
            }

            override fun loadFromDb(): Flowable<List<MovieEntity>> {
                val movieEntityList = movieDAO.getAllMovie()
                return if (movieEntityList == null || movieEntityList.isEmpty()) {
                    Flowable.empty()
                } else {
                    Flowable.just(movieEntityList)
                }
            }

            override fun createCall(): Observable<Resource<MovieResponse>> {
                return movieApiServices.getMoviesPopular(page = 1)
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
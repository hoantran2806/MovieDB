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
import io.reactivex.functions.Function4
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieDAO: MovieDAO,
    private val movieApiServices: ApiServices
) {
    private val TAG = "MovieRepository_Hoan"
    fun loadMovieByType(page: Long): Observable<Resource<List<MovieEntity>>> {
        return object : NetworkBoundResource<List<MovieEntity>, MovieResponse>() {

            override fun saveCallResult(item: MovieResponse) {
                Log.i(TAG, "4 - Call method saveCallResult: ")
                val movieEntities = ArrayList<MovieEntity>()
                for (entity: MovieEntity in item.results) {
                    entity.page = item.page.toLong()
                    entity.totalPages = item.totalPages.toLong()
//                    entity.genres= arrayListOf(Genre(24,"dasda"), Genre(24,"dasda"));
                    movieEntities.add(entity)
                }

                val result = movieDAO.insertMovies(movieEntities)
                Log.i(TAG, "saveCallResult: ${result.size}")
            }

            override fun shouldFetch(): Boolean {
                return true
            }

            override fun loadFromDb(): Flowable<List<MovieEntity>> {
//                Log.i(TAG, "Call method loadFromDb: ")
                val movieEntityList = movieDAO.getMovieByPage(page)
                return if (movieEntityList == null || movieEntityList.isEmpty()) {
                    Flowable.empty()
                } else {
                    Log.i(
                        TAG,
                        "5 - loadFromDb: Flowable.just(movieEntityList) size = ${movieEntityList.size}"
                    )
                    Flowable.just(movieEntityList)
                }
            }

            override fun createCall(): Observable<Resource<MovieResponse>> {
                Log.i(TAG, "2 - createCall: Call method createCall() in MovieRepository")
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

    fun loadMovieDetail(id: Long): Observable<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieEntity>() {
            override fun saveCallResult(item: MovieEntity) {
                val movieEntity = movieDAO.getMovieById(id)
                if (movieEntity == null) {
                    movieDAO.insertMovie(item)
                } else {
                    item.page = movieEntity.page
                    item.totalPages = movieEntity.totalPages
                    movieDAO.updateMovie(item)
                }
            }

            override fun shouldFetch(): Boolean {
                return true
            }

            override fun loadFromDb(): Flowable<MovieEntity> {
                val movieEntity = movieDAO.getMovieById(id)
                if (movieEntity == null) {
                    return Flowable.empty()
                } else {
                    return Flowable.just(movieEntity)
                }
            }

            override fun createCall(): Observable<Resource<MovieEntity>> {
                val movieId = id.toString()
                return Observable.combineLatest(
                    movieApiServices.getMovieDetail(movieId),
                    movieApiServices.getMovieCredits(movieId),
                    movieApiServices.getSimilarMovies(movieId),
                    movieApiServices.getMovieVideos(movieId),
                    Function4 { movieEntity,
                                creditResponse,
                                similarMovieResponse,
                                videosResponse ->
                        if (creditResponse != null) {
                            Log.i(
                                "loadMovieDetail",
                                "createCall: creditResponse = ${creditResponse.cast.size}"
                            )
                            movieEntity.cast = creditResponse.cast
                            movieEntity.crew = creditResponse.crew
                        }
                        if (similarMovieResponse != null) {
                            Log.i(
                                "loadMovieDetail",
                                "createCall: similarMovieResponse = ${similarMovieResponse.results[0]}"
                            )
                            movieEntity.similarMovie = similarMovieResponse.results
                        }
                        if (videosResponse != null) {
                            Log.i(
                                "loadMovieDetail",
                                "createCall: videosResponse = ${videosResponse.results.toString()}"
                            )
                            movieEntity.video = videosResponse.results
                        }
                        Resource.success(movieEntity)
                    })
            }

        }.getAsObservable()
    }
}
package hoannt.android.moviedb.data

import hoannt.android.moviedb.data.local.entity.MovieEntity
import hoannt.android.moviedb.data.network.model.Genre
import hoannt.android.moviedb.data.network.model.MovieResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    @GET("movie/popular?language=en-US&region=US&page=1")
    fun getMoviesPopular(): Observable<MovieResponse>

    @GET("/3/movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") movieId: String): Observable<MovieEntity>

    @GET("/3/genre/movie/list")
    fun getMovieGenres(): Single<List<Genre>>
}
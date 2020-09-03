package hoannt.android.moviedb.data

import hoannt.android.moviedb.data.local.entity.MovieEntity
import hoannt.android.moviedb.data.network.model.Genre
import hoannt.android.moviedb.data.network.model.MovieResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("movie/popular")
    fun getMoviesPopular(
        @Query("language") language: String = "en-US",
        @Query("page") page: Long,
        @Query("region") region: String = "US"
    ): Observable<MovieResponse>

    @GET("/3/movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") movieId: String): Observable<MovieEntity>

    @GET("/3/genre/movie/list")
    fun getMovieGenres(): Single<List<Genre>>
}
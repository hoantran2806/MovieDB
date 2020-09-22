package hoannt.android.moviedb.data

import hoannt.android.moviedb.data.local.entity.MovieEntity
import hoannt.android.moviedb.data.network.model.Genre
import hoannt.android.moviedb.data.network.model.MovieResponse
import hoannt.android.moviedb.data.network.model.VideoResponse
import hoannt.android.moviedb.data.network.model.credit.CreditResponse
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

    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") movieId: String): Observable<MovieEntity>

    @GET("genre/movie/list")
    fun getMovieGenres(): Single<List<Genre>>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(@Path("movie_id") movieId: String): Observable<CreditResponse>

    @GET("movie/{movie_id}/similar")
    fun getSimilarMovies(
        @Path("movie_id") movieId: String,
        @Query("page") page: Long = 1L
    ): Observable<MovieResponse>

    @GET("movie/{movie_id}/videos")
    fun getMovieVideos(@Path("movie_id") movieId: String): Observable<VideoResponse>

    @GET("search/multi")
    fun search(
        @Query("language") language: String = "en-US",
        @Query("query") query: String,
        @Query("page") page: Long = 1L,
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("region") region: String? = null
    ): Single<MovieResponse>
}
package hoannt.android.moviedb.data.network.model


import com.google.gson.annotations.SerializedName
import hoannt.android.moviedb.data.local.entity.MovieEntity

data class MovieResponse(
    val page: Int,
    val results: List<MovieEntity>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
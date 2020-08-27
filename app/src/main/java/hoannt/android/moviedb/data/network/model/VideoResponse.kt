package hoannt.android.moviedb.data.network.model


data class VideoResponse(
    var id: Int,
    var results: List<Video>
)
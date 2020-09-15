package hoannt.android.moviedb.data.local.entity


import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import hoannt.android.moviedb.data.local.converter.*
import hoannt.android.moviedb.data.network.model.Genre
import hoannt.android.moviedb.data.network.model.Video
import hoannt.android.moviedb.data.network.model.credit.Cast
import hoannt.android.moviedb.data.network.model.credit.Crew
import java.util.*


@Entity(primaryKeys = ["id"])
data class MovieEntity(

    var budget: Double,
    var page: Long,
    var totalPages: Long,
    @SerializedName("id")
    val id: Long,
    var overview: String?,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("release_date")
    var releaseDate: String?,
    var runtime: Long,
    var status: String?,
    var title: String?,
    @TypeConverters(GenresConverter::class)
    var genres: List<Genre>? = ArrayList(),
    @SerializedName("videos")
    @TypeConverters(VideoConverter::class)
    var video: List<Video>? = ArrayList(),
    @TypeConverters(CastConverter::class)
    var cast: List<Cast>? = ArrayList(),
    @TypeConverters(CrewConverter::class)
    var crew: List<Crew>? = ArrayList(),
    @TypeConverters(MovieListTypeConverter::class)
    var similarMovie: List<MovieEntity>? = ArrayList()

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readLong(),
        parcel.readLong(),

        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Genre.CREATOR),
        parcel.createTypedArrayList(Video.CREATOR),
        parcel.createTypedArrayList(Cast.CREATOR),
        parcel.createTypedArrayList(Crew.CREATOR),
        parcel.createTypedArrayList(CREATOR)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(budget)
        parcel.writeLong(page)
        parcel.writeLong(totalPages)
        parcel.writeLong(id)
        parcel.writeString(overview)
        parcel.writeString(posterPath)
        parcel.writeString(releaseDate)
        parcel.writeLong(runtime)
        parcel.writeString(status)
        parcel.writeString(title)
        parcel.writeTypedList(genres)
        parcel.writeTypedList(video)
        parcel.writeTypedList(cast)
        parcel.writeTypedList(crew)
        parcel.writeTypedList(similarMovie)
    }

    override fun describeContents(): Int {
        return 0
    }

    fun isLastPage(): Boolean {
        return page >= totalPages
    }

    companion object CREATOR : Parcelable.Creator<MovieEntity> {
        override fun createFromParcel(parcel: Parcel): MovieEntity {
            return MovieEntity(parcel)
        }

        override fun newArray(size: Int): Array<MovieEntity?> {
            return arrayOfNulls(size)
        }
    }

}



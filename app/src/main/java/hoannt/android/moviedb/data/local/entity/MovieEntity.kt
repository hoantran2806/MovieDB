package hoannt.android.moviedb.data.local.entity


import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id"])
data class MovieEntity(

    var budget: Double,
    var page: Long,
    var totalPages: Long,
//    @TypeConverters(GenresConverter::class)
//    var genres: List<Genre>? = ArrayList(),
    @SerializedName("id")
    val id: Long,
    var overview: String?,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("release_date")
    var releaseDate: String?,
    var runtime: Long,
    var status: String?,
    var title: String?
//    @TypeConverters(VideoConverter::class)
//    var video: List<Video>? = ArrayList(),
//    @SerializedName("vote_average")
//    var voteAverage: Double?,
//    @SerializedName("vote_count")
//    var voteCount: Int?
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
        parcel.readString()
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



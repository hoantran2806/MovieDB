package hoannt.android.moviedb.data.network.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.TypeConverters
import hoannt.android.moviedb.data.local.converter.VideoConverter


data class VideoResponse(
    var id: Int,
    @TypeConverters(VideoConverter::class)
    var results: List<Video>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.createTypedArrayList(Video)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeTypedList(results)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoResponse> {
        override fun createFromParcel(parcel: Parcel): VideoResponse {
            return VideoResponse(parcel)
        }

        override fun newArray(size: Int): Array<VideoResponse?> {
            return arrayOfNulls(size)
        }
    }

}
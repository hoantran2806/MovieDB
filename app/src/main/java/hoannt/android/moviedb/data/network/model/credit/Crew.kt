package hoannt.android.moviedb.data.network.model.credit


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Crew(
    @SerializedName("credit_id")
    var creditId: String?,
    var department: String?,
    var gender: Int,
    var id: Int,
    var job: String?,
    var name: String?,
    @SerializedName("profile_path")
    var profilePath: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(creditId)
        parcel.writeString(department)
        parcel.writeInt(gender)
        parcel.writeInt(id)
        parcel.writeString(job)
        parcel.writeString(name)
        parcel.writeString(profilePath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Crew> {
        override fun createFromParcel(parcel: Parcel): Crew {
            return Crew(parcel)
        }

        override fun newArray(size: Int): Array<Crew?> {
            return arrayOfNulls(size)
        }
    }

}
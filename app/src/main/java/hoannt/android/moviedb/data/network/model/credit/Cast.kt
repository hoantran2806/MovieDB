package hoannt.android.moviedb.data.network.model.credit


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("cast_id")
    var castId: Int,
    var character: String?,
    @SerializedName("credit_id")
    var creditId: String?,
    var gender: Int,
    var id: Int,
    var name: String?,
    var order: Int,
    @SerializedName("profile_path")
    var profilePath: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(castId)
        parcel.writeString(character)
        parcel.writeString(creditId)
        parcel.writeInt(gender)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(order)
        parcel.writeString(profilePath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cast> {
        override fun createFromParcel(parcel: Parcel): Cast {
            return Cast(parcel)
        }

        override fun newArray(size: Int): Array<Cast?> {
            return arrayOfNulls(size)
        }
    }

}
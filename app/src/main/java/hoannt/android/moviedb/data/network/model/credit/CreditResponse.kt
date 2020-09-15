package hoannt.android.moviedb.data.network.model.credit


import android.os.Parcel
import android.os.Parcelable
import androidx.room.TypeConverters
import hoannt.android.moviedb.data.local.converter.CastConverter
import hoannt.android.moviedb.data.local.converter.CrewConverter
import java.util.*

data class CreditResponse(
    @TypeConverters(CastConverter::class)
    var cast: List<Cast> = ArrayList(),

    @TypeConverters(CrewConverter::class)
    var crew: List<Crew> = ArrayList()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(Cast)!!,
        parcel.createTypedArrayList(Crew)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(cast)
        parcel.writeTypedList(crew)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CreditResponse> {
        override fun createFromParcel(parcel: Parcel): CreditResponse {
            return CreditResponse(parcel)
        }

        override fun newArray(size: Int): Array<CreditResponse?> {
            return arrayOfNulls(size)
        }
    }

}
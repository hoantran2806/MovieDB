package hoannt.android.moviedb.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hoannt.android.moviedb.data.network.model.credit.Cast

class CastConverter {

    @TypeConverter
    fun fromList(list: List<Cast>?): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromString(value: String): List<Cast>? {
        var type = object : TypeToken<List<Cast>>() {}.type
        return Gson().fromJson<List<Cast>>(value, type)
    }
}
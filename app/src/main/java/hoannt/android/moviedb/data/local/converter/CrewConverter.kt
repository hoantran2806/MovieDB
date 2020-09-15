package hoannt.android.moviedb.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hoannt.android.moviedb.data.network.model.credit.Crew

class CrewConverter {

    @TypeConverter
    fun fromList(list: List<Crew>?): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromString(value: String): List<Crew>? {
        var type = object : TypeToken<List<Crew>>() {}.type
        return Gson().fromJson<List<Crew>>(value, type)
    }
}
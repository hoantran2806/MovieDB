package hoannt.android.moviedb.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hoannt.android.moviedb.data.network.model.Genre

class GenresConverter {

    @TypeConverter
    fun fromList(list: List<Genre>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromString(value: String): List<Genre>? {
        val type = object : TypeToken<List<Genre>>() {}.type
        return Gson().fromJson(value, type)
    }
}
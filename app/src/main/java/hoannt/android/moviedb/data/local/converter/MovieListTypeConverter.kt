package hoannt.android.moviedb.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hoannt.android.moviedb.data.local.entity.MovieEntity

class MovieListTypeConverter {

    @TypeConverter
    fun fromList(list: List<MovieEntity>?): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromString(value: String): List<MovieEntity>? {
        var type = object : TypeToken<List<MovieEntity>>() {}.type
        return Gson().fromJson<List<MovieEntity>>(value, type)
    }
}
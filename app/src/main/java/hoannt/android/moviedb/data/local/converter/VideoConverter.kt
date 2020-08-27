package hoannt.android.moviedb.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hoannt.android.moviedb.data.network.model.Video

class VideoConverter {

    @TypeConverter
    fun fromList(list: List<Video>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromString(value: String): List<Video>? {
        val type = object : TypeToken<List<Video>>() {}.type
        return Gson().fromJson(value, type)
    }
}
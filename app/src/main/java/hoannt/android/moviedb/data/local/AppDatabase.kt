package hoannt.android.moviedb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hoannt.android.moviedb.data.local.converter.*
import hoannt.android.moviedb.data.local.dao.MovieDAO
import hoannt.android.moviedb.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], exportSchema = false, version = 1)
@TypeConverters(
    GenresConverter::class, VideoConverter::class, CastConverter::class,
    CrewConverter::class, MovieListTypeConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDAO

}
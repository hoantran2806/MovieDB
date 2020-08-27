package hoannt.android.moviedb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hoannt.android.moviedb.data.local.entity.MovieEntity
import io.reactivex.Flowable

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(value: MovieEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(list: List<MovieEntity>): LongArray

    @Query("SELECT * FROM MovieEntity")
    fun getAllMovie(): List<MovieEntity>

    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    fun getMovieById(id: Long): MovieEntity

    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    fun getMovieDetailById(id: Long): Flowable<MovieEntity>
}
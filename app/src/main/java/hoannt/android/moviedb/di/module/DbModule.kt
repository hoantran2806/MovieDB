package hoannt.android.moviedb.di.module

import android.app.Application
import androidx.annotation.NonNull
import androidx.room.Room
import dagger.Module
import dagger.Provides
import hoannt.android.moviedb.data.local.AppDatabase
import hoannt.android.moviedb.data.local.dao.MovieDAO
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    internal fun provideDatabase(@NonNull application: Application): AppDatabase {
        return Room.databaseBuilder(
            application, AppDatabase::class.java, "Entertaiment.db"
        )
            .allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    internal fun provideMovieDao(@NonNull appDatabase: AppDatabase): MovieDAO {
        return appDatabase.movieDao()
    }
}
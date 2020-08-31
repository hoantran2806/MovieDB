package hoannt.android.moviedb.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import hoannt.android.moviedb.ui.MovieListFragment

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeMovieListFragment(): MovieListFragment
}
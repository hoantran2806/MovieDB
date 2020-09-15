package hoannt.android.moviedb.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import hoannt.android.moviedb.ui.detail.MovieDetailFragment
import hoannt.android.moviedb.ui.list.MovieListFragment

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeMovieListFragment(): MovieListFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieDetailFragment(): MovieDetailFragment
}
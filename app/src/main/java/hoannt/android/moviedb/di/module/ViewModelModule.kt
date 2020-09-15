package hoannt.android.moviedb.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import hoannt.android.moviedb.ui.detail.viewmodel.MovieDetailViewModel
import hoannt.android.moviedb.ui.list.viewmodel.MovieListViewmodel

@Module
internal abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewmodel::class)
    protected abstract fun movieListViewModel(movieListViewmodel: MovieListViewmodel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    protected abstract fun movieDetailViewModel(movieDetailViewModel: MovieDetailViewModel): ViewModel

}
package hoannt.android.moviedb.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import hoannt.android.moviedb.ui.MainActivity

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}
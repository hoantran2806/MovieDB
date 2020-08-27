package hoannt.android.moviedb.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import hoannt.android.moviedb.AppController
import hoannt.android.moviedb.di.module.ActivityModule
import hoannt.android.moviedb.di.module.ApiModule
import hoannt.android.moviedb.di.module.DbModule
import hoannt.android.moviedb.di.module.ViewModelModule
import javax.inject.Singleton

@Component(
    modules = [ApiModule::class, DbModule::class, ActivityModule::class,
        ViewModelModule::class, AndroidSupportInjectionModule::class]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Buider {

        @BindsInstance
        fun application(application: Application): Buider


        fun build(): AppComponent
    }

    fun inject(appController: AppController)
}
package hoannt.android.moviedb

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import hoannt.android.moviedb.di.component.DaggerAppComponent
import javax.inject.Inject

class AppController : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity>? = dispatchingAndroidInjector


    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().application(this)
            .build().inject(this)
    }
}
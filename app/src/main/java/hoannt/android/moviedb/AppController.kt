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

    var daggerApplication = DaggerAppComponent.builder().application(this)
        .build()

    override fun onCreate() {
        super.onCreate()
        daggerApplication.inject(this)
    }

}
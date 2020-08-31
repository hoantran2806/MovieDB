package hoannt.android.moviedb

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import hoannt.android.moviedb.data.local.repository.MovieRepository
import javax.inject.Inject

class TestBroadcast : BroadcastReceiver() {
    @Inject
    lateinit var repo: MovieRepository
    private val TAG = "Broadcast_MinhLam"
    override fun onReceive(context: Context?, intent: Intent?) {
        (context?.applicationContext as AppController).daggerApplication.inject(this)
        repo.loadMovieByType()
        Log.e(TAG, "onReceive: ${repo.loadMovieByType()}")
    }
}
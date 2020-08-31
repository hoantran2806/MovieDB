package hoannt.android.moviedb.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hoannt.android.moviedb.data.local.entity.MovieEntity

class ShareMovieViewModel : ViewModel() {

    val selected = MutableLiveData<MovieEntity>()

    fun select(movieEntity: MovieEntity) {
        selected.value = movieEntity
    }
}
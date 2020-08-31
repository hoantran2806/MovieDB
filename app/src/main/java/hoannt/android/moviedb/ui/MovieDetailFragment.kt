package hoannt.android.moviedb.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import hoannt.android.moviedb.AppConstants
import hoannt.android.moviedb.R
import hoannt.android.moviedb.ui.viewmodel.ShareMovieViewModel
import kotlinx.android.synthetic.main.fragment_movie_detail.*


class MovieDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var sharedMovieViewModel =
            ViewModelProvider(requireActivity()).get(ShareMovieViewModel::class.java)
        var movieEntity = sharedMovieViewModel.selected.value
        Picasso.get().load(AppConstants.IMAGE_URL + movieEntity?.posterPath).into(movie_image)
        movie_title.text = movieEntity?.title
        movie_overview.text = movieEntity?.overview
        Toast.makeText(requireActivity(), "${movieEntity?.title}", Toast.LENGTH_SHORT).show()


    }
}
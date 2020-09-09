package hoannt.android.moviedb.ui

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import hoannt.android.moviedb.AppConstants
import hoannt.android.moviedb.R
import hoannt.android.moviedb.ui.viewmodel.ShareMovieViewModel
import kotlinx.android.synthetic.main.fragment_movie_detail.*


class MovieDetailFragment : Fragment() {

    lateinit var imageUri: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageUri = requireArguments().getString("uri").toString()
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack(R.id.destination_movie, false)

            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var sharedMovieViewModel =
            ViewModelProvider(requireActivity()).get(ShareMovieViewModel::class.java)
        var movieEntity = sharedMovieViewModel.selected.value
        movie_image.apply {
            transitionName = imageUri
            Picasso.get().load(AppConstants.IMAGE_URL + movieEntity?.posterPath).into(movie_image)
        }

//        Picasso.get().load(AppConstants.IMAGE_URL + movieEntity?.posterPath).into(movie_image)

        movie_title.text = movieEntity?.title
        movie_overview.text = movieEntity?.overview
        Toast.makeText(requireActivity(), "${movieEntity?.title}", Toast.LENGTH_SHORT).show()


    }
}
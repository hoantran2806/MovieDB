package hoannt.android.moviedb.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hoannt.android.moviedb.AppConstants
import hoannt.android.moviedb.R
import hoannt.android.moviedb.data.local.entity.MovieEntity

class SimilarMovieListAdapter(
    private var similarMovieList: MutableList<MovieEntity>,
    private var onClickDetailSimilarMovie: OnClickDetailSimilarMovie
) :
    RecyclerView.Adapter<SimilarMovieListAdapter.SimilarViewHolder>() {


    class SimilarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view.findViewById<ImageView>(R.id.similar_movie_preview)

        fun bind(movieEntity: MovieEntity) {
            Picasso.get().load(AppConstants.IMAGE_URL + movieEntity.posterPath).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.similar_movie_holder,
            parent, false
        )
        return SimilarViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (similarMovieList.isNullOrEmpty()) {
            return 0
        }
        return similarMovieList.size
    }

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
        val similarMovie = similarMovieList[position]
        holder.imageView.setOnClickListener {
            onClickDetailSimilarMovie.onDetailClick(position)
        }
        holder.bind(similarMovie)
    }

    fun setList(list: List<MovieEntity>) {
        if (this.similarMovieList.isNullOrEmpty()) {
            this.similarMovieList.addAll(list)
        }
//        this.similarMovieList = list
//        notifyDataSetChanged()
    }

    fun getList(): List<MovieEntity> {
        return similarMovieList
    }

    fun getSimilarMovieDetail(position: Int): MovieEntity {
        return similarMovieList[position]
    }

    interface OnClickDetailSimilarMovie {
        fun onDetailClick(position: Int)
    }
}
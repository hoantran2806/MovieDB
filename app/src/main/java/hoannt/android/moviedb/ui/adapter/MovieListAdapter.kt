package hoannt.android.moviedb.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hoannt.android.moviedb.AppConstants
import hoannt.android.moviedb.R
import hoannt.android.moviedb.data.local.entity.MovieEntity
import kotlinx.android.synthetic.main.movie_item_layout.view.*

class MovieListAdapter(private var movieList: List<MovieEntity>) :
    RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {


    inner class MovieListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var movieEntity: MovieEntity? = null

        fun bind(movieEntity: MovieEntity) {
            this.movieEntity = movieEntity
            Picasso.get().load(AppConstants.IMAGE_URL + movieEntity.posterPath)
                .into(itemView.movie_image)
            itemView.movie_title.text = movieEntity.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.movie_item_layout,
            parent, false
        )
        return MovieListViewHolder(view)
    }

    override fun getItemCount(): Int = movieList.size


    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val movieEntity = movieList[position]
        holder.bind(movieEntity)
    }

    fun setList(movieList: List<MovieEntity>) {
        this.movieList = movieList
    }
}
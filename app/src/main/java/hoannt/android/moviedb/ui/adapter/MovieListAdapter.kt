package hoannt.android.moviedb.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hoannt.android.moviedb.AppConstants
import hoannt.android.moviedb.R
import hoannt.android.moviedb.data.local.entity.MovieEntity

class MovieListAdapter(
    private var movieList: MutableList<MovieEntity>,
    private val recyclerViewItemClick: RecyclerViewItemClick
) :
    RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {
    private val TAG = "MovieListAdapte_MinhLam"
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    inner class MovieListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.movie_image)

        fun bind(movieEntity: MovieEntity) {
//            this.movieEntity = movieEntity
            imageView.apply {
                transitionName = movieEntity.posterPath
                Picasso.get().load(AppConstants.IMAGE_URL + movieEntity.posterPath)
                    .into(this)
            }
            itemView.setOnClickListener {
                recyclerViewItemClick.onItemSelected(movieEntity, imageView)
            }
//            itemView.movie_title.text = movieEntity.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
//        if(viewType == VIEW_TYPE_ITEM){
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.movie_item_layout,
            parent, false
        )
        return MovieListViewHolder(view)
//        }else{
//            val view = LayoutInflater.from(parent.context).inflate(
//                R.layout.loading_holder,
//                parent, false
//            )
//            return LoadingViewHolder(view)
//        }

    }

    override fun getItemCount(): Int {
        if (movieList == null) {
            return 0
        } else {
            return movieList.size
        }
    }


    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
//        if (holder is MovieListViewHolder){
        val movieEntity = movieList[position]
//        holder.itemView.setOnClickListener {
//            recyclerViewItemClick.onItemSelected(position)
//        }
        holder.bind(movieEntity)
//        }
//        else if (holder is LoadingViewHolder){
//            holder.itemView.progress_Bar
//        }

    }

    fun setList(movieList: List<MovieEntity>) {
        val startPosition = this.movieList.size
        Log.i(
            TAG,
            "setList: startPosition = $startPosition & movieList.size = ${movieList.size} & this.movieList.size = ${this.movieList.size}"
        )
        this.movieList.addAll(movieList)
        Log.i(TAG, "setList: this.movieList.size = ${this.movieList.size}")
        notifyItemRangeChanged(startPosition, movieList.size)
    }

    fun getList(): List<MovieEntity> {
        return movieList
    }

    fun getItem(position: Int): MovieEntity {
        return movieList[position]
    }


    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface RecyclerViewItemClick {
        fun onItemSelected(movieEntity: MovieEntity, imageView: ImageView)
    }
    //    override fun getItemViewType(position: Int): Int {
//        if (position == movieList.size){
//            return VIEW_TYPE_LOADING
//        } else{
//            return VIEW_TYPE_ITEM
//        }
//    }
}
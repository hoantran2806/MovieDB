package hoannt.android.moviedb.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import hoannt.android.moviedb.R
import hoannt.android.moviedb.data.network.model.Video

class VideoListAdapter(private var videoList: List<Video>) :
    RecyclerView.Adapter<VideoListAdapter.VideoViewHolder>() {

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.video_preview)

        fun bind(video: Video) {
            imageView.setImageResource(R.drawable.ic_white_live_tv_24)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.video_viewholder_layout
            , parent, false
        )
        return VideoViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (videoList.isNullOrEmpty()) 0
        return videoList.size
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videoList[position]
        holder.bind(video)
    }

    fun setList(videoList: List<Video>) {
        this.videoList = videoList
        notifyDataSetChanged()
    }

    fun getList(): List<Video> {
        return videoList
    }

    fun getVideoItem(position: Int): Video {
        return videoList[position]
    }
}
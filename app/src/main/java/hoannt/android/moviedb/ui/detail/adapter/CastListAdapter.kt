package hoannt.android.moviedb.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hoannt.android.moviedb.AppConstants
import hoannt.android.moviedb.R
import hoannt.android.moviedb.data.network.model.credit.Cast

class CastListAdapter(private var castList: List<Cast>) :
    RecyclerView.Adapter<CastListAdapter.CastViewHolder>() {

    class CastViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val castImage = view.findViewById<ImageView>(R.id.cast_image)
        private val castName = view.findViewById<TextView>(R.id.tv_cast_name)

        fun bind(cast: Cast) {
            Picasso.get().load(AppConstants.IMAGE_URL + cast.profilePath)
                .into(castImage)
            castName.text = cast.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.cast_viewholder_layout,
            parent, false
        )
        return CastViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (castList.isNullOrEmpty()) {
            return 0
        }
        return castList.size
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val cast = castList[position]
        holder.bind(cast)
    }

//    fun setList(castList: List<Cast>) {
//        if (this.castList.isNullOrEmpty()) {
//            this.castList.addAll(castList)
//            notifyDataSetChanged()
//        }
////        this.castList = castList
////        notifyDataSetChanged()
//    }

    fun getList(): List<Cast> {
        return castList
    }

    fun getCastDetail(position: Int): Cast {
        return castList[position]
    }
}
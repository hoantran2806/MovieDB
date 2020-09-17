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
import hoannt.android.moviedb.data.network.model.credit.Crew

class CrewListAdapter(private var crewList: MutableList<Crew>) :
    RecyclerView.Adapter<CrewListAdapter.CrewHolder>() {

    class CrewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val crewImage: ImageView = view.findViewById(R.id.crew_image)
        private val crewName: TextView = view.findViewById(R.id.tv_crew_name)

        fun bind(crew: Crew) {
            Picasso.get().load(AppConstants.IMAGE_URL + crew.profilePath)
                .error(R.drawable.ic_baseline_error_24).into(crewImage)
            crewName.text = crew.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.crew_viewholder_layout,
            parent, false
        )
        return CrewHolder(view)
    }

    override fun getItemCount(): Int {
        if (crewList.isNullOrEmpty()) {
            return 0
        }
        return crewList.size
    }

    override fun onBindViewHolder(holder: CrewHolder, position: Int) {
        var crew = crewList[position]
        holder.bind(crew)
    }

    fun setList(crewList: List<Crew>) {
        if (this.crewList.isNullOrEmpty()) {
            this.crewList.addAll(crewList)
            notifyDataSetChanged()
        }
//        this.crewList.clear()
//        notifyDataSetChanged()
    }

    fun getList(): List<Crew> {
        return crewList
    }

    fun getCrewDetail(position: Int): Crew {
        return crewList[position]
    }
}
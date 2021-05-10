import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gabriel.estg.cleancity.R
import gabriel.estg.cleancity.adapters.NoteListAdapter
import gabriel.estg.cleancity.api.Ocorrency
import gabriel.estg.cleancity.notes.database.entities.Note
import org.w3c.dom.Text
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class OcorrencesListAdapter : ListAdapter<Ocorrency, OcorrencesListAdapter.OcorrencyViewHolder>(OcorrencesListAdapter.OcorrencesComparator()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OcorrencyViewHolder {
        return OcorrencyViewHolder.create(parent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: OcorrencyViewHolder, position: Int) {

        //Formatter of date
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")


        var current = getItem(position)
        var isExpandable: Boolean = current.expandable

        holder.occorenceId.text = current.id.toString()
        holder.categoryItemView.text = current.category_id.toString()
        holder.subCategoryItemView.text = current.subCategory_id.toString()
        holder.streetNameItemView.text = current.street
        holder.pointOfReferenceItemView.text = current.reference_point
        holder.descriptionItemView.text = current.description
        holder.dateItemView.text = current.date.format(formatter)

        holder.expandableLayout.visibility = if (isExpandable) View.VISIBLE else View.GONE
        holder.linealLayout.setOnClickListener {
            current.expandable = !current.expandable
            notifyItemChanged(position)
        }
    }

    class OcorrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val occorenceId: TextView = itemView.findViewById(R.id.ocorrenceId)
        val categoryItemView: TextView = itemView.findViewById(R.id.ocorenceCategoryBD)
        val subCategoryItemView:TextView = itemView.findViewById(R.id.ocorrenceReciclerSubCategoryBD)
        val streetNameItemView:TextView = itemView.findViewById(R.id.ocorrenceReciclerStreetBd)
        val pointOfReferenceItemView:TextView = itemView.findViewById(R.id.ocorrenceReciclerPointOfReferenceBD)
        val descriptionItemView:TextView = itemView.findViewById(R.id.ocorrenceReciclerDescriptionBD)
        val dateItemView : TextView = itemView.findViewById(R.id.ocorrenceReciclerDateDB)

        val linealLayout: LinearLayout = itemView.findViewById(R.id.occorenceItemLinearLayout)
        val expandableLayout: RelativeLayout = itemView.findViewById(R.id.ocorrences_expandable_layout)

        companion object {
            fun create(parent: ViewGroup): OcorrencyViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.reciclerview_ocorrences_item, parent, false)
                return OcorrencyViewHolder(view)
            }
        }
    }


    class OcorrencesComparator : DiffUtil.ItemCallback<Ocorrency>() {
        override fun areItemsTheSame(oldItem: Ocorrency, newItem: Ocorrency): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Ocorrency, newItem: Ocorrency): Boolean {
            return oldItem.id == newItem.id
        }
    }

}
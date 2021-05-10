package gabriel.estg.cleancity.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gabriel.estg.cleancity.R
import gabriel.estg.cleancity.notes.database.entities.Note


class NoteListAdapter : ListAdapter<Note, NoteListAdapter.NoteViewHolder>(NotesComparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        var current = getItem(position)
        var isExpandable: Boolean = current.expandable

        holder.noteId.text = current.id.toString()
        holder.noteItemView.text = current.assunto
        holder.streetItemView.text = current.rua
        holder.localityItemView.text = current.cidade
        holder.postalCodeItemView.text = current.codigo_postal
        holder.dateItemView.text = current.data
        holder.observationsItemView.text = current.observacoes

        holder.expandableLayout.visibility = if (isExpandable) View.VISIBLE else View.GONE
        holder.linealLayout.setOnClickListener {
            current.expandable = !current.expandable
            notifyItemChanged(position)
        }
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteId: TextView = itemView.findViewById(R.id.noteId)
        val noteItemView: TextView = itemView.findViewById(R.id.subject)
        val streetItemView: TextView = itemView.findViewById(R.id.reciclerStreetBD)
        val localityItemView: TextView = itemView.findViewById(R.id.reciclerLocalityDB)
        val postalCodeItemView: TextView = itemView.findViewById(R.id.reciclerPostalCodeDB)
        val dateItemView: TextView = itemView.findViewById(R.id.reciclerDateDB)
        val observationsItemView: TextView = itemView.findViewById(R.id.reciclerObservationsDB)
        val linealLayout: LinearLayout = itemView.findViewById(R.id.linearLayout)
        val expandableLayout: RelativeLayout = itemView.findViewById(R.id.expandable_layout)

        companion object {
            fun create(parent: ViewGroup): NoteViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return NoteViewHolder(view)
            }
        }
    }

    class NotesComparator : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }
    }

}
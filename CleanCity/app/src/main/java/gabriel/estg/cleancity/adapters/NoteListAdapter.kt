package gabriel.estg.cleancity.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gabriel.estg.cleancity.R
import gabriel.estg.cleancity.database.entities.Note

class NoteListAdapter : ListAdapter<Note, NoteListAdapter.NoteViewHolder>(NotesComparator()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        var current = getItem(position)

        holder.bind(current.assunto)
        Log.i("adapter","ID = "+current.id + " Assunto = "+current.assunto )

        var isExpandable : Boolean=current.expandable

        holder.expandableLayout.visibility = if(isExpandable) View.VISIBLE else View.GONE

        holder.linealLayout.setOnClickListener{
            current.expandable =!current.expandable
            notifyItemChanged(position)
        }

    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val noteItemView: TextView = itemView.findViewById(R.id.subject)
        val linealLayout : LinearLayout = itemView.findViewById(R.id.linearLayout)
        val expandableLayout : ConstraintLayout = itemView.findViewById(R.id.expandable_layout)

        fun bind(text: String?) {
            noteItemView.text = text
        }

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
package androidkotlin.formation.notepad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class NoteAdapter(private val notes: List<Note>, private val itemClickListener: View.OnClickListener)
    : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val titleView: TextView = itemView.findViewById(R.id.title)
        val excerptView: TextView = itemView.findViewById(R.id.excerpt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)

        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]

        holder.cardView.setOnClickListener(itemClickListener)
        holder.cardView.tag = position
        holder.titleView.text = note.title
        holder.excerptView.text = note.text
    }

    override fun getItemCount(): Int {
        return notes.size
    }

}
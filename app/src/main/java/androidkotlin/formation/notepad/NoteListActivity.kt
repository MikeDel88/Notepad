package androidkotlin.formation.notepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidkotlin.formation.notepad.utils.loadNotes
import androidkotlin.formation.notepad.utils.persistNote
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class NoteListActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var notes: MutableList<Note>
    lateinit var adapter: NoteAdapter
    lateinit var coordinatorLayout: CoordinatorLayout
    private val startDetailActivity = registerForActivityResult(NoteDetailActivityContract()) { result ->
        if(result != null) {
            processEditNoteResult(result)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        // Toolbar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Liste des Notes
        notes = loadNotes(this)

        // CoordinatorLayout + FloatingActionListener
        coordinatorLayout = findViewById(R.id.coordinator_layout)
        findViewById<FloatingActionButton>(R.id.create_note_fab).setOnClickListener(this)

        // RecyclerView + Adapter
        adapter = NoteAdapter(notes, this)
        val recyclerView = findViewById<RecyclerView>(R.id.notes_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this) // en vertical par défaut.
        recyclerView.adapter = adapter
    }

    private fun processEditNoteResult(data: Intent) {
        val noteIndex = data.getIntExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, -1)

        when(data.action) {
            NoteDetailActivity.ACTION_SAVE_NOTE -> {
                val note = data.getParcelableExtra<Note>(NoteDetailActivity.EXTRA_NOTE)
                saveNote(noteIndex, note!!)
            }
            NoteDetailActivity.ACTION_DELETE_NOTE -> {
                deleteNote(noteIndex)
            }
        }


    }

    private fun deleteNote(noteIndex: Int) {
        if(noteIndex < 0)
            return

        val note = notes.removeAt(noteIndex)
        androidkotlin.formation.notepad.utils.deleteNote(this, note)
        adapter.notifyDataSetChanged()

        // Permet d'afficher que le note a bien été supprimer avec un effet
        Snackbar.make(coordinatorLayout, "${note.title} supprimé", Snackbar.LENGTH_LONG)
            .show()
    }

    private fun saveNote(index: Int, note: Note) {
        persistNote(this, note)
        if(index < 0) {
            notes.add(0, note)
            adapter.notifyItemChanged(0)
        }
        else {
            notes[index] = note
            adapter.notifyItemChanged(index)
        }

    }

    override fun onClick(view: View) {
        if(view.tag != null) {
            showNoteDetail(view.tag as Int)
        } else {
            when(view.id) {
                R.id.create_note_fab -> createNewNote()
            }
        }

    }

    private fun createNewNote() {
        showNoteDetail(-1)
    }

    private fun showNoteDetail(noteIndex: Int) {
        val note = if(noteIndex < 0) Note() else notes[noteIndex]
        startDetailActivity.launch(Pair(noteIndex, note))
    }
}
package androidkotlin.formation.notepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView

class NoteDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOTE = "note"
        const val EXTRA_NOTE_INDEX = "noteIndex"

        const val ACTION_SAVE_NOTE = "androidkotlin.formation.notepad.actions.ACTION_SAVE_NOTE"
        const val ACTION_DELETE_NOTE = "androidkotlin.formation.notepad.actions.ACTION_DELETE_NOTE"
    }

    lateinit var note: Note
    private var noteIndex: Int = -1

    lateinit var titleView: TextView
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        note = intent.getParcelableExtra<Note>(EXTRA_NOTE) as Note
        noteIndex = intent.getIntExtra(EXTRA_NOTE_INDEX, -1)

        titleView = findViewById(R.id.title)
        textView = findViewById(R.id.text)

        titleView.text = note.title
        textView.text = note.text
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_note_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_save -> {
                saveNote()
                return true
            }
            R.id.action_delete -> {
                showConfirmeDeleteDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showConfirmeDeleteDialog() {
        val confirmFragment = ConfirmDeleteDialogFragment(note.title)
        confirmFragment.listener = object : ConfirmDeleteDialogFragment.ConfirmeDeleteDialogListener {
            override fun onDialogPositiveClick() {
                deleteNote()
            }
            override fun onDialogNegativeClick() {}
        }
        confirmFragment.show(supportFragmentManager, "ConfirmDialogShow")
    }

    private fun saveNote() {
        note.title = titleView.text.toString()
        note.text = textView.text.toString()

        intent = Intent().apply {
            action = ACTION_SAVE_NOTE
            putExtra(EXTRA_NOTE, note as Parcelable) // On cast pour savoir si on le veut en Parcelable ou Serialisable
            putExtra(EXTRA_NOTE_INDEX, noteIndex)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun deleteNote() {
        intent = Intent().apply {
            action = ACTION_DELETE_NOTE
            putExtra(EXTRA_NOTE_INDEX, noteIndex)
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}
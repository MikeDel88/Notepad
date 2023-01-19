package androidkotlin.formation.notepad.utils

import android.content.Context
import android.util.Log
import androidkotlin.formation.notepad.Note
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

private const val TAG = "storage"

fun persistNote(context: Context, note: Note) {
    if(note.filename.isEmpty()) {
        note.filename = UUID.randomUUID().toString() + ".note"
    }
    val fileOutput = context.openFileOutput(note.filename, Context.MODE_PRIVATE)
    val outputStream = ObjectOutputStream(fileOutput)
    outputStream.writeObject(note)
    outputStream.close()
}

fun loadNotes(context: Context) : MutableList<Note> {
    val notes = mutableListOf<Note>()

    // on récupère le répertoire ou se trouve les notes
    val notesDir = context.filesDir
    // On itere sur la liste des noms de fichiers.
    for(filename in notesDir.list()!!) {
        val note = loadNote(context, filename)
        Log.i(TAG, "Loaded note $note")
        notes.add(note)
    }

    return notes
}

fun deleteNote(context: Context, note: Note) {
    context.deleteFile(note.filename)
}

private fun loadNote(context: Context, filename: String): Note {
    val fileInput = context.openFileInput(filename)
    val inputStream = ObjectInputStream(fileInput)
    val note = inputStream.readObject() as Note
    inputStream.close()

    return note
}

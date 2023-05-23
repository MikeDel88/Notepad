package androidkotlin.formation.notepad.utils

import android.content.Context
import android.util.Log
import androidkotlin.formation.notepad.Note
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

private const val TAG = "storage"

/**
 * Ouvre/Crée un fichier puis l'assigne à une propriété de Note - Ecrit dans le fichier.
 * Le fichier est stocké dans les files du context de l'application.
 * @param context - Context de l'application
 * @param note - Objet que l'on souhaite conserver.
 */
fun persistNote(context: Context, note: Note) {
    if(note.filename.isEmpty()) {
        note.filename = UUID.randomUUID().toString() + ".note"
    }
    val fileOutput = context.openFileOutput(note.filename, Context.MODE_PRIVATE)
    val outputStream = ObjectOutputStream(fileOutput)
    outputStream.writeObject(note)
    outputStream.close()
}

/**
 * Charge la liste des fichiers depuis le context de l'application
 * @param context - Context de l'application.
 * @return une liste d'objets Note
 */
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

/**
 * On supprime le fichier présent dans les files du context
 * en utilisant le nom du fichier (propriété de l'objet Note)
 * @param context - Context de l'application
 * @param note - Objet que l'on souhaite supprimer
 */
fun deleteNote(context: Context, note: Note) {
    context.deleteFile(note.filename)
}

/**
 * Charge une note depuis un nom de fichier
 * utiliser lors du chargement de l'ensemble des notes.
 * @param context - Context de l'application
 * @filename - Nom du fichier que l'on souhaite charger et lire.
 * @return un objet Note.
 */
private fun loadNote(context: Context, filename: String): Note {
    val fileInput = context.openFileInput(filename)
    val inputStream = ObjectInputStream(fileInput)
    val note = inputStream.readObject() as Note
    inputStream.close()

    return note
}

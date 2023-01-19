package androidkotlin.formation.notepad

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Parcelable
import androidkotlin.formation.notepad.NoteDetailActivity.Companion.EXTRA_NOTE
import androidkotlin.formation.notepad.NoteDetailActivity.Companion.EXTRA_NOTE_INDEX
import androidx.activity.result.contract.ActivityResultContract

/**
 * Contrat de résultat de l'activity DetailActivity avec index et la note.
 */
class NoteDetailActivityContract: ActivityResultContract<Pair<Int, Note>, Intent?>() {

    override fun createIntent(context: Context, input: Pair<Int, Note>): Intent {
        return Intent(context, NoteDetailActivity::class.java).apply {
            putExtra(EXTRA_NOTE_INDEX, input.first)
            putExtra(EXTRA_NOTE, input.second as Parcelable) // On cast pour savoir si on le veut en Parcelable ou Serialisable
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Intent? {
        return if(resultCode == Activity.RESULT_OK && intent != null) intent
        else null
    }
}
package androidkotlin.formation.notepad

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ConfirmDeleteDialogFragment(private val noteTitle: String? = ""): DialogFragment() {

    interface ConfirmeDeleteDialogListener {
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

    var listener: ConfirmeDeleteDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        builder.setMessage("Êtes-vous sûr de supprimer la note $noteTitle ?")
            .setPositiveButton("Supprimer",
                DialogInterface.OnClickListener { dialog, which -> listener?.onDialogPositiveClick()})
            .setNegativeButton("Annuler",
                DialogInterface.OnClickListener { dialog, which -> listener?.onDialogNegativeClick()})

        return builder.create()
    }
}
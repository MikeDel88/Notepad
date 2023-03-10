package androidkotlin.formation.notepad

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
// Parcelable sert à transmettre depuis les putExtra et Serialisable permet d'écrire l'objet dans un fichier.
data class Note(var title: String = "", var text: String = "", var filename: String = "")
    : Parcelable, Serializable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(text)
        parcel.writeString(filename)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        // Identifiant unique qui permet à la JVM de reconnaître note version de la classe Note.
        private val serialVersionUid: Long = 4242424242
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}
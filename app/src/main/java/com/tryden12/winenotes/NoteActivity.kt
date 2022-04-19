package com.tryden12.winenotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.tryden12.winenotes.database.AppDatabase
import com.tryden12.winenotes.database.Note
import com.tryden12.winenotes.databinding.ActivityNoteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteBinding
    private var purpose : String? = ""
    private var noteId : Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Get intent and apply title
        intent = (intent).apply {
            purpose = intent.getStringExtra(
                getString(R.string.intent_purpose_key)
            )
            title = "${purpose} Note"
        }

        if (purpose.equals("Update")) {
            noteId = intent.getLongExtra(
                getString(R.string.intent_note_id),
                -1
            )

            // Load exiting note from db
            CoroutineScope(Dispatchers.IO).launch {
                val note = AppDatabase.getDatabase(applicationContext)
                    .noteDao()
                    .getNote(noteId)

                withContext(Dispatchers.Main) {
                    binding.titleEditText.setText(note.title)
                    binding.titleEditText.setText(note.notes)
                }
            }

        }

    }

    override fun onBackPressed() {
        val date = ""
        val title = binding.titleEditText.text.toString().trim()
        if (title.isEmpty()) {
            Toast.makeText(applicationContext,
            "Title cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val notes = binding.notesEditText.text.toString().trim()
        if (notes.isEmpty()) {
            Toast.makeText(applicationContext,
            "Notes cannot be left blank", Toast.LENGTH_SHORT).show()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            val noteDao = AppDatabase.getDatabase(applicationContext).noteDao()

            if (purpose.equals(getString(R.string.intent_purpose_add_note))) {
                // add note to db
                val note = Note(0, title, notes, date)
                noteId = noteDao.addNote(note)

                Log.i("STATUS_NOTE", "inserted new note: $note")
            } else {
                // update the notes in the db
                val note = Note(noteId, title, notes, date)
                noteDao.updateNote(note)

                Log.i("STATUS_NOTE", "updated existing note: $note")
            }

            val intent = Intent()
            intent.putExtra(
                getString(R.string.intent_note_id),
                noteId
            )

            withContext(Dispatchers.Main) {
                setResult(RESULT_OK, intent)
                super.onBackPressed()
            }
        }
    }
}
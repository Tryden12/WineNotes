package com.tryden12.winenotes

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

class NoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteBinding
    private var purpose : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        intent = getIntent()
        purpose = intent.getStringExtra(
            getString(R.string.intent_purpose_key)
        )
        title = "${purpose} Note"
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

            val resultId : Long

            if (purpose.equals(getString(R.string.intent_purpose_add_note))) {
                // add note to db
                val note = Note(0, title, notes, date)
                resultId = noteDao.addNote(note)

                Log.i("STATUS_NOTE", "inserted new note: ${note}")
            } else {
                // update the notes in the db
                TODO("NOT IMPLEMENTED")
            }

        }
        super.onBackPressed()
    }
}
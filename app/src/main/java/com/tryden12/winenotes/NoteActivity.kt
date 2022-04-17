package com.tryden12.winenotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.tryden12.winenotes.databinding.ActivityNoteBinding

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
        super.onBackPressed()
    }
}
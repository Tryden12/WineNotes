package com.tryden12.winenotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        super.onBackPressed()
    }
}
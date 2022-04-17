package com.tryden12.winenotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tryden12.winenotes.databinding.ActivityNoteBinding

class NoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

    override fun onBackPressed() {

        super.onBackPressed()
    }
}
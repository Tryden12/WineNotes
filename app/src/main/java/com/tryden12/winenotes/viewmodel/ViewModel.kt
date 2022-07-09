package com.tryden12.winenotes.viewmodel
import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import com.tryden12.winenotes.R
import com.tryden12.winenotes.adapter.Adapter
import com.tryden12.winenotes.data.database.AppDatabase
import com.tryden12.winenotes.data.database.Note
import com.tryden12.winenotes.view.NoteActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModel : ViewModel() {

    private val notes = mutableListOf<Note>()
    private lateinit var adapter : Adapter

    fun loadAllNotes() {
        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getDatabase(this)
            val dao = db.noteDao()
            val results = dao.getAllNotes()

            withContext(Dispatchers.Main) {
                notes.clear()
                notes.addAll(results)

                adapter = Adapter()
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun addNewNote() {
        val intent = Intent(this, NoteActivity::class.java)
        intent.putExtra(
            R.string.intent_purpose_key), "")
        startForAddResult.launch(intent)
    }

    private val startForAddResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result : ActivityResult ->

            if (result.resultCode == Activity.RESULT_OK) {
                loadAllNotes()
            }
        }

    private val startForUpdateResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result : ActivityResult ->

            if (result.resultCode == Activity.RESULT_OK) {
                loadAllNotes()
            }
        }


    private fun sortNotesByTitle() {
        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getDatabase(applicationContext)
            val dao = db.noteDao()
            val results = dao.sortByTitle()

            withContext(Dispatchers.Main) {
                notes.clear()
                notes.addAll(results)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun sortNotesByLastModified() {
        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getDatabase(applicationContext)
            val dao = db.noteDao()
            val results = dao.sortByLastModified()

            withContext(Dispatchers.Main) {
                notes.clear()
                notes.addAll(results)
                adapter.notifyDataSetChanged()
            }
        }
    }

}
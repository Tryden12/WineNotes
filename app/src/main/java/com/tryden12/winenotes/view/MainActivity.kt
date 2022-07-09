package com.tryden12.winenotes.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tryden12.winenotes.R
import com.tryden12.winenotes.adapter.Adapter
import com.tryden12.winenotes.data.database.AppDatabase
import com.tryden12.winenotes.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter : Adapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Layout Manager
        val layoutManager = LinearLayoutManager(this)
        binding.myrecyclerview.layoutManager = layoutManager

        // Add divider
        val divider = DividerItemDecoration(
            applicationContext, layoutManager.orientation
        )
        binding.myrecyclerview.addItemDecoration(divider)

        // Bind adapter to recycler view
        adapter = Adapter()
        binding.myrecyclerview.adapter = adapter


        // Add Note Button
        binding.addNoteImageButton.setOnClickListener {
            addNewNote()
        }
        binding.addNoteTextView.setOnClickListener {
            addNewNote()
        }


        loadAllNotes()
    }


    /********************     Options Menu    ********************************************************/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_add_note) {
            addNewNote()
            return true
        } else if (item.itemId == R.id.menu_item_sort_title) {
            sortNotesByTitle()
            return true
        } else if (item.itemId == R.id.menu_sort_date) {
            sortNotesByLastModified()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /********************     RecyclerView Inner Classes    ************************************/
    inner class MyViewHolder(val view: TextView) :
        RecyclerView.ViewHolder(view),
        View.OnClickListener, View.OnLongClickListener {


        init {
            view.setOnClickListener(this)
            view.setOnLongClickListener(this)
        }


        override fun onClick(view: View?) {
            val intent = Intent(applicationContext, NoteActivity::class.java)

            intent.putExtra(
                getString(R.string.intent_purpose_key),
                getString(R.string.intent_update_note)
            )

            val note = notes[adapterPosition]
            intent.putExtra(
                getString(R.string.intent_note_id),
                note.id
            )

            startForUpdateResult.launch(intent)
        }

        override fun onLongClick(view: View?): Boolean {
            val note = notes[adapterPosition]

            val builder = AlertDialog.Builder(view!!.context)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete " +
                    "${note.title}?")
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok) {
                    dialogInterface, whichButton ->

                    CoroutineScope(Dispatchers.IO).launch {
                        AppDatabase.getDatabase(applicationContext)
                            .noteDao()
                            .deleteNote(note)

                        loadAllNotes()
                    }
                }
            builder.show()
            return true
        }

    }

    // Adapter
    inner class MyAdapter() :
        RecyclerView.Adapter<MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view, parent, false) as TextView
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val note = notes[position]
            holder.view.text = note.title
        }

        override fun getItemCount(): Int {
            return notes.size
        }

    }



}
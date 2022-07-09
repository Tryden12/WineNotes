package com.tryden12.winenotes.adapter

import android.app.AlertDialog
import android.content.Intent
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tryden12.winenotes.R
import com.tryden12.winenotes.data.database.AppDatabase
import com.tryden12.winenotes.data.database.Note
import com.tryden12.winenotes.view.MainActivity
import com.tryden12.winenotes.view.NoteActivity
import com.tryden12.winenotes.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Adapter() :
RecyclerView.Adapter<MainActivity.MyViewHolder>() {

    private val notes = mutableListOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false) as TextView
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainActivity.MyViewHolder, position: Int) {
        val note = notes[position]
        holder.view.text = note.title
    }

    override fun getItemCount(): Int {
        return notes.size
    }


    inner class MyViewHolder(private val view: TextView) :
    RecyclerView.ViewHolder(view),
    View.OnClickListener, View.OnLongClickListener {


        init {
            view.setOnClickListener(this)
            view.setOnLongClickListener(this)
        }


        override fun onClick(view: View?) {
            val intent = Intent(this, NoteActivity::class.java)

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
                        AppDatabase.getDatabase(this)
                            .noteDao()
                            .deleteNote(note)

                        ViewModel().loadAllNotes()
                    }
                }
            builder.show()
            return true
        }

    }

}
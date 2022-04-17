package com.tryden12.winenotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tryden12.winenotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter : MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Layout Manager
        val layoutManager = LinearLayoutManager(this)
        binding.myrecyclerview.setLayoutManager(layoutManager)

        // Add divider
        val divider = DividerItemDecoration(
            applicationContext, layoutManager.orientation
        )
        binding.myrecyclerview.addItemDecoration(divider)

        // Bind adapter to recycler view
        adapter = MyAdapter()
        binding.myrecyclerview.setAdapter(adapter)
    }

/********************     Options Menu    ********************************************************/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_sort_title) {
            return true
        } else if (item.itemId == R.id.menu_sort_date) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /********************     RecyclerView Inner Classes    ************************************/
    inner class MyViewHolder(val itemView : View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {


        init {
            itemView.findViewById<View>(R.id.item_constraintLayout)
                .setOnClickListener(this)
            itemView.findViewById<View>(R.id.item_constraintLayout)
                .setOnLongClickListener(this)
        }


        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

        override fun onLongClick(v: View?): Boolean {
            TODO("Not yet implemented")
        }

    }

    // Adapter
    inner class MyAdapter() : RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            TODO("Not yet implemented")
        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }

    }

    /********************     Methods    ********************************************************/

    private fun addNewNote() {
        val intent = Intent(applicationContext, NoteActivity::class.java)
    }
}
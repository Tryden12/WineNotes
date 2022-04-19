package com.tryden12.winenotes.database

import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    fun addNote(note : Note) : Long

    @Update
    fun updateNote(note : Note)

    @Delete
    fun deleteNote(note : Note)


    /**
     * Deletes EVERYTHING in the table
     */
    @Query("DELETE FROM note")
    fun deleteAllNotes()

    @Query("SELECT * FROM note")
    fun getAllNotes() : List<Note>
}
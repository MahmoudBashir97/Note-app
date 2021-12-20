package com.mahmoudbashir.note_app.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mahmoudbashir.note_app.model.NoteModel

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewNote(note:NoteModel)

    @Update
    suspend fun updateNote(note: NoteModel)

    @Delete
    suspend fun deleteNote(note: NoteModel)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes():LiveData<List<NoteModel>>

    @Query("SELECT * FROM notes WHERE noteTitle LIKE :query OR noteBody LIKE :query")
    fun searchOnNotes(query:String?):LiveData<List<NoteModel>>

}
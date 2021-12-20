package com.mahmoudbashir.note_app.repository

import androidx.lifecycle.LiveData
import com.mahmoudbashir.note_app.model.NoteModel
import com.mahmoudbashir.note_app.roomdb.NoteDatabase

class NoteRepository(private val db:NoteDatabase) : INoteRepository{


    override suspend fun addNewNote(note: NoteModel)  = db.getDao().addNewNote(note)
    override suspend fun updateNote(note: NoteModel) = db.getDao().updateNote(note)

    override suspend fun deleteNote(note: NoteModel) = db.getDao().deleteNote(note)

    override  fun getAllNotes() = db.getDao().getAllNotes()

    override fun searchOnNotes(query:String?): LiveData<List<NoteModel>> = db.getDao().searchOnNotes(query)

}
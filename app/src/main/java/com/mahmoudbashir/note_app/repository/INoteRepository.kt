package com.mahmoudbashir.note_app.repository

import androidx.lifecycle.LiveData
import com.mahmoudbashir.note_app.model.NoteModel

interface INoteRepository {

    suspend fun addNewNote(note:NoteModel)

    suspend fun updateNote(note: NoteModel)

    suspend fun deleteNote(note: NoteModel)

     fun getAllNotes():LiveData<List<NoteModel>>

     fun searchOnNotes(query:String?):LiveData<List<NoteModel>>
}
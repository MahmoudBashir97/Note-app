package com.mahmoudbashir.note_app.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mahmoudbashir.note_app.model.NoteModel
import com.mahmoudbashir.note_app.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel (
    app:Application,
    private val repo: NoteRepository
        ) :AndroidViewModel(app){


    val mlist:MutableLiveData<List<NoteModel>> = MutableLiveData()


    fun addNewNote(note:NoteModel) = viewModelScope.launch {
        repo.addNewNote(note)
    }

    fun updateNote(note: NoteModel) = viewModelScope.launch {
        repo.updateNote(note)
    }
    fun deleteNote(note: NoteModel) = viewModelScope.launch {
        repo.deleteNote(note)
    }

    fun getAllNotes() :LiveData<List<NoteModel>>{
        return repo.getAllNotes()
    }

    fun searchOnNotes(query:String?):LiveData<List<NoteModel>>{
        return repo.searchOnNotes(query)
    }

}
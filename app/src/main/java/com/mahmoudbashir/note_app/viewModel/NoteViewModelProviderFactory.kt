package com.mahmoudbashir.note_app.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudbashir.note_app.repository.NoteRepository

class NoteViewModelProviderFactory(
    private val app:Application,
    private val noteRepository: NoteRepository
) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(app,noteRepository) as T
    }
}
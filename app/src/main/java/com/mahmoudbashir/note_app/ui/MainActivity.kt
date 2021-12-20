package com.mahmoudbashir.note_app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.mahmoudbashir.note_app.databinding.ActivityMainBinding
import com.mahmoudbashir.note_app.repository.NoteRepository
import com.mahmoudbashir.note_app.roomdb.NoteDatabase
import com.mahmoudbashir.note_app.viewModel.NoteViewModel
import com.mahmoudbashir.note_app.viewModel.NoteViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    lateinit var viewModel : NoteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)
        setUpViewModel()

    }

    private fun setUpViewModel() {
        val repo = NoteRepository(NoteDatabase(this))
        val viewModelFactory = NoteViewModelProviderFactory(
            application,
            repo
        )
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(NoteViewModel::class.java)
    }
}
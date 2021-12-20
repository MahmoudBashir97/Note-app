package com.mahmoudbashir.note_app.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.mahmoudbashir.note_app.R
import com.mahmoudbashir.note_app.databinding.FragmentUpdateNoteBinding
import com.mahmoudbashir.note_app.model.NoteModel
import com.mahmoudbashir.note_app.ui.MainActivity
import com.mahmoudbashir.note_app.viewModel.NoteViewModel

class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {

    private var _binding :FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel:NoteViewModel
    lateinit var currentNote:NoteModel
    private val args:UpdateNoteFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        menu.clear()
        inflater.inflate(R.menu.menu_update_note,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                deleteNote()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Are you sure for deleting this note?")
            setPositiveButton("DELETE"){_,_->
                viewModel.deleteNote(currentNote)
                view?.findNavController()?.navigate(R.id.action_updateNoteFragment_to_homeFragment)

            }
            setNegativeButton("Cancel",null)
        }.create().show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateNoteBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        currentNote = args.note!!

        if(currentNote != null) setCurrentDataToView(currentNote.noteTitle,currentNote.noteBody)

        saveUpdatedNote()

    }

    private fun saveUpdatedNote() {
        binding.fabDone.setOnClickListener {
            val title = binding.etNoteTitleUpdate.text.toString().trim()
            val body = binding.etNoteBodyUpdate.text.toString().trim()

            if (title.isNotEmpty()){
                val note = NoteModel(currentNote.id,title,body)
                viewModel.updateNote(note)
                view?.findNavController()?.navigate(R.id.action_updateNoteFragment_to_homeFragment)
            }else{
                Toast.makeText(context,"Enter a note title please",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setCurrentDataToView(noteTitle: String, noteBody: String) {
        binding.etNoteTitleUpdate.setText(noteTitle)
        binding.etNoteBodyUpdate.setText(noteBody)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
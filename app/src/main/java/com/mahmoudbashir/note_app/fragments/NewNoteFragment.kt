package com.mahmoudbashir.note_app.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mahmoudbashir.note_app.R
import com.mahmoudbashir.note_app.databinding.FragmentNewNoteBinding
import com.mahmoudbashir.note_app.model.NoteModel
import com.mahmoudbashir.note_app.ui.MainActivity
import com.mahmoudbashir.note_app.viewModel.NoteViewModel


class NewNoteFragment : Fragment(R.layout.fragment_new_note) {

    private var _binding: FragmentNewNoteBinding? =null
    private val binding get() = _binding!!
    lateinit var mView:View

    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menu_save -> saveNote(mView)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_new_note,menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNewNoteBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        mView = view
    }


    private fun saveNote(view:View){
        val noteTitle = binding.etNoteTitle.text.toString().trim()
        val noteBody = binding.etNoteBody.text.toString().trim()

        if (noteTitle.isNotEmpty()){
            val note = NoteModel(0,noteTitle,noteBody)

            val check = viewModel.addNewNote(note).isActive.and(isAdded)
            if (check){
            Snackbar.make(view,
            "Note Saved Successfully!",
            Snackbar.LENGTH_LONG)
                .show()

                view.findNavController().navigate(
                    R.id.action_newNoteFragment_to_homeFragment
                )
            }else{
                Toast.makeText(context,"some issues occured while saving data to room db !!",Toast.LENGTH_LONG).show()
            }

        }else{
            Toast.makeText(context,"Please Enter Note Title!!",Toast.LENGTH_LONG).show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
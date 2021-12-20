package com.mahmoudbashir.note_app.fragments

import android.os.Bundle
import android.view.*

import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mahmoudbashir.note_app.R
import com.mahmoudbashir.note_app.adapters.NoteAdapter
import com.mahmoudbashir.note_app.databinding.FragmentHomeBinding
import com.mahmoudbashir.note_app.model.NoteModel
import com.mahmoudbashir.note_app.ui.MainActivity
import com.mahmoudbashir.note_app.viewModel.NoteViewModel


class HomeFragment : Fragment(R.layout.fragment_home) ,
    NoteAdapter.onClickNoteItemInterface,
    SearchView.OnQueryTextListener
{

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: NoteViewModel
    lateinit var note_adapter:NoteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.home_menu,menu)

        val mMenuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        mMenuSearch.isSubmitButtonEnabled = true
        mMenuSearch.setOnQueryTextListener(this)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment

        _binding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )

        viewModel = (activity as MainActivity).viewModel


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAddNote.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNewNoteFragment())
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        note_adapter = NoteAdapter(this)
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
            adapter = note_adapter
        }
        activity?.let {
            viewModel.getAllNotes().observe(viewLifecycleOwner,
                {mlist->
                    if (mlist != null){
                        note_adapter.differ.currentList.clear()
                        note_adapter.differ.submitList(mlist)
                        updateUI(mlist)
                    }

                })
        }
    }

    private fun updateUI(note:List<NoteModel>) {
        if (note.isNotEmpty()){
            binding.recyclerView.visibility = View.VISIBLE
            binding.tvNoNoteAvailable.visibility = View.GONE
        }else{
            binding.recyclerView.visibility = View.GONE
            binding.tvNoNoteAvailable.visibility = View.VISIBLE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(note: NoteModel, position: Int) {
        /*Toast.makeText(context,"Clicked ",Toast.LENGTH_LONG).show()*/
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUpdateNoteFragment(note))
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        /*if(query != null){
            searchNotes(query)
        }*/
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        if(newText != null){
            searchNotes(newText)
        }

        return true
    }

    private fun searchNotes(query:String?){

        val searchQuery = "%$query"

        viewModel.searchOnNotes(searchQuery).observe(this,{
            list -> note_adapter.differ.submitList(list)
            note_adapter.notifyDataSetChanged()
        })
    }
}
package com.mahmoudbashir.note_app.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.AsyncListUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudbashir.note_app.databinding.NoteItemLayoutBinding
import com.mahmoudbashir.note_app.model.NoteModel
import java.util.*

class NoteAdapter (private val IonClickItem: onClickNoteItemInterface): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    interface onClickNoteItemInterface{
        fun onClick(note:NoteModel,position: Int)
    }

    private var binding : NoteItemLayoutBinding? = null


    class ViewHolder(itemBinding:NoteItemLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    }

    private val differCallback=
        object :DiffUtil.ItemCallback<NoteModel>(){
            override fun equals(other: Any?): Boolean {
                return super.equals(other)
            }

            override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
                TODO("Not yet implemented")
            }

            override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
                TODO("Not yet implemented")
            }
        }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = NoteItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context)
                ,parent, false
        )
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNoteItem = differ.currentList[position]

        holder.itemView.apply {
            binding?.tvNoteTitle?.text = currentNoteItem.noteTitle
            binding?.tvNoteBody?.text = currentNoteItem.noteBody

            val random = Random()
            val color = Color.argb(
                255,
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)
            )

            binding?.cardView?.setBackgroundColor(color)

        }

        holder.itemView.setOnClickListener {
            IonClickItem.onClick(currentNoteItem,position)
        }
    }

    override fun getItemCount()= differ.currentList.size
}
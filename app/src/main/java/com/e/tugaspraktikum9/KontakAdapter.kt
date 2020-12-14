package com.e.tugaspraktikum9

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.e.tugaspraktikum9.Kontak.Kontak
import kotlinx.android.synthetic.main.kontak_item.view.*

class KontakAdapter : ListAdapter<Kontak, KontakAdapter.NoteHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Kontak>() {
            override fun areItemsTheSame(oldItem: Kontak, newItem: Kontak): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Kontak, newItem: Kontak): Boolean {
                return oldItem.Nama == newItem.Nama && oldItem.Description == newItem.Description && oldItem.Phone == newItem.Phone && oldItem.Priority == newItem.Priority
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.kontak_item, parent, false)
        return NoteHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote: Kontak = getItem(position)

        holder.textViewTitle.text = currentNote.Nama
        holder.textViewPriority.text = currentNote.Priority.toString()
        holder.textViewPhone.text = currentNote.Phone
        holder.textViewDescription.text = currentNote.Description
    }

    fun getNoteAt(position: Int): Kontak {
        return getItem(position)
    }

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener{
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var textViewTitle: TextView = itemView.text_view_title
        var textViewPriority: TextView = itemView.text_view_priority
        var textViewPhone: TextView = itemView.text_view_phone
        var textViewDescription: TextView = itemView.text_view_description
    }

    interface OnItemClickListener {
        fun onItemClick(note: Kontak)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}
package com.a203110026.fanianggita_room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.a203110026.fanianggita_room.data.CatatanEntitas
import kotlinx.android.synthetic.main.item_catatan.view.*

class CatatanAdapter : ListAdapter<CatatanEntitas, CatatanAdapter.NotesViewHolder>(diffCallback) {

    private lateinit var listener: OnItemClickListener

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<CatatanEntitas>() {
            override fun areItemsTheSame(oldItem: CatatanEntitas, newItem: CatatanEntitas): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CatatanEntitas, newItem: CatatanEntitas): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_catatan, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {

        val item = getItem(position)

        holder.itemView.tvTitle.text = item.judulcatatan

        holder.itemView.tvDateTime.text = item.dateTime
    }

    fun setOnClickListener(listener1: OnItemClickListener) {
        listener = listener1
    }

    inner class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnItemClickListener {
        fun onClicked(notesId: Int)
    }
}

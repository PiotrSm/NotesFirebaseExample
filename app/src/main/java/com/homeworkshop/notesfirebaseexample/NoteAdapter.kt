package com.homeworkshop.notesfirebaseexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.note_item.view.*

class NoteAdapter(options: FirestoreRecyclerOptions<Note>) : FirestoreRecyclerAdapter<Note, NoteAdapter.NoteViewHolder>(
    options
) {
    lateinit var  listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return  NoteViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.note_item,parent, false
           ))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int, model: Note) {
        holder.itemView.text_view_title.text = model.title
        holder.itemView.text_view_description.text = model.description
        holder.itemView.text_view_priority.text = model.priority.toString()
    }

    fun deleteItem(position:Int){
        snapshots.getSnapshot(position).reference.delete()
    }

   inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
       val item = itemView
       init {
           itemView.setOnClickListener{
               val position = adapterPosition
               if(position != RecyclerView.NO_POSITION && listener != null){
                   listener.onItemClick(snapshots.getSnapshot(position),position)
               }

           }
       }
   }

    interface OnItemClickListener{
        fun onItemClick(documentSnapshot: DocumentSnapshot, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }
}


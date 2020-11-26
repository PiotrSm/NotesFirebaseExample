package com.homeworkshop.notesfirebaseexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    val notebookRef = db.collection("Notebook")
    lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_add_note.setOnClickListener{
            startActivity(Intent(this,AddNote::class.java))
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val query = notebookRef.orderBy("priority", Query.Direction.DESCENDING)

        val options = FirestoreRecyclerOptions.Builder<Note>().setQuery(query, Note::class.java).build()

        adapter = NoteAdapter(options)

        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(applicationContext)

        val itemTouchHelper = object: ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.deleteItem(viewHolder.adapterPosition)
            }

        }
        ItemTouchHelper(itemTouchHelper).apply { attachToRecyclerView(recycler_view) }

        adapter.setOnItemClickListener(object :NoteAdapter.OnItemClickListener{
            override fun onItemClick(documentSnapshot: DocumentSnapshot, position: Int) {
                val note = documentSnapshot.toObject(Note::class.java)
                val id = documentSnapshot.id
                val path = documentSnapshot.reference.path

                Toast.makeText(this@MainActivity,"Position: $position ID: $id", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}
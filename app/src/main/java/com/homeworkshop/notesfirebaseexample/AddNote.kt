package com.homeworkshop.notesfirebaseexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.ContentView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_note2.*

class AddNote : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note2)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        title = "Add note"

        number_picker_priority.maxValue = 10
        number_picker_priority.minValue = 1
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.save_note -> {
                saveNote()
                return true
            }
            else ->{
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun saveNote() {
        if(edit_text_title.text.isNullOrEmpty()){
           Toast.makeText(this,"Insert at least title", Toast.LENGTH_LONG).show()
           return
        }

        val notebookref = FirebaseFirestore.getInstance().collection("Notebook")
        notebookref.add(Note(edit_text_title.text.toString(),edit_text_description.text.toString(),number_picker_priority.value))
        Toast.makeText(this,"Note added", Toast.LENGTH_LONG).show()
        finish()
    }
}
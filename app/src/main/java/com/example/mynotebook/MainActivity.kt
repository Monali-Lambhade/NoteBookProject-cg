package com.example.mynotebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterface {
    lateinit var noteRV: RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var viewModal: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportActionBar?.setTitle("Quick Notes")

        noteRV= findViewById(R.id.idRVNotes)
        addFAB= findViewById(R.id.idFABAddNote)
        noteRV.layoutManager= LinearLayoutManager(this)

        val noteRVAdapter= NoteRVAdapter(this,this, this)
         noteRV.adapter = noteRVAdapter
        viewModal= ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).
        get(NoteViewModel::class.java)
        viewModal.allNote.observe(this,{ list->
            list?.let {
                noteRVAdapter.updateList(it)
            }
        })

        addFAB.setOnClickListener{
            val intent= Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    override fun onDeleteIconClick(note: Note)
    {
        viewModal.deleteNote(note)
        Toast.makeText(this, "${note.noteTitle} Deleted", Toast.LENGTH_LONG).show()

    }

    override fun onNoteClick(note: Note) {


        val intent= Intent(this@MainActivity, AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescriptor)
        intent.putExtra("noteId" , note.id)
        startActivity(intent)
        this.finish()

    }
}
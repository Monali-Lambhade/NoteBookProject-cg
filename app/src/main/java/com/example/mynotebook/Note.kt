package com.example.mynotebook


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName ="notesTable" )
data class Note (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val noteTitle:String,
    val noteDescriptor: String,
    val timestamp: String)


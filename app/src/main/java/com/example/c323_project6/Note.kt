package com.example.c323_project6

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Declare the Note item that will be used throughout project.
 * noteId : Long that contains the id of the note
 * noteTitle : String that contains title of the note
 * noteText : String that contains the actual content of the note
 */
@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0L,
    @ColumnInfo(name = "note_title")
    var noteTitle: String = "",
    @ColumnInfo(name = "note_content")
    var noteText: String = ""
)

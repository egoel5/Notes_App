package com.example.c323_project6

import com.google.firebase.database.Exclude

data class Note(
    @get:Exclude
    var noteId: String = "",
    var noteTitle: String = "",
    var noteContent: String = ""
)

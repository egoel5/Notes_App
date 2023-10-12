package com.example.c323_project6

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
class NotesViewModel(val dao: NoteDao) : ViewModel() {
    var newNoteTitle = "New Note"
    val notes = dao.getAll()
    private val _navigateToNote = MutableLiveData<Long?>()
    val navigateToNote: LiveData<Long?>
        get() = _navigateToNote
    /**
     * Add a new note with the newNoteTitle string being the new Note's title
     */
    fun addNote() {
        viewModelScope.launch {
            val note = Note()
            note.noteTitle = newNoteTitle
            dao.insert(note)
        }
    }
    /**
     * Delete note based on note's value
     */
    fun deleteNote(taskId: Long) {
        viewModelScope.launch {
            val note = Note()
            note.noteId = taskId
            dao.delete(note)
        }
    }
    /**
     * make sure navigateToList's value is proper so it doesn't go to EditNoteFragment when it shouldn't
     */

    fun onNoteClicked(taskId: Long) {
        _navigateToNote.value = taskId
    }
    fun onNoteNavigated() {
        _navigateToNote.value = null
    }
}
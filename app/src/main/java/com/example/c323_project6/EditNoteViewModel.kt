package com.example.c323_project6

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
class EditNoteViewModel(noteId: Long, val dao: NoteDao) : ViewModel() {
    // get note from dao then initialize navigateToList
    val note = dao.get(noteId)
    private val _navigateToList = MutableLiveData<Boolean>(false)
    val navigateToList: LiveData<Boolean>
        get() = _navigateToList

    /**
     * Update note based on note's value
     */
    fun updateNote() {
        viewModelScope.launch {
            dao.update(note.value!!)
            _navigateToList.value = true
        }
    }

    /**
     * Delete note based on note's value
     */
    fun deleteNote() {
        viewModelScope.launch {
            dao.delete(note.value!!)
            _navigateToList.value = true
        }
    }

    /**
     * make sure navigateToList's value is false so it doesn't go back to mainFragment
     */
    fun onNavigatedToList() {
        _navigateToList.value = false
    }

}
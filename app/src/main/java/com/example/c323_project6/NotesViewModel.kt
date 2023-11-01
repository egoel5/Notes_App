package com.example.c323_project6

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import kotlinx.coroutines.launch
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
class NotesViewModel() : ViewModel() {
    // initialize Firebase auth and all associated variables such as navigation gets
    private var auth: FirebaseAuth
    var user: User = User()
    var verifyPassword = ""
    var noteId : String = ""
    var note = MutableLiveData<Note>()
    private val _notes : MutableLiveData<MutableList<Note>> = MutableLiveData()
    val notes : LiveData<List<Note>>
        get() = _notes as LiveData<List<Note>>
    private val _navigateToNote = MutableLiveData<String?>()
    val navigateToNote: LiveData<String?>
        get() = _navigateToNote

    private val _navigateToList = MutableLiveData<Boolean>(false)
    val navigateToList: LiveData<Boolean>
        get() = _navigateToList

    private val _errorHappened = MutableLiveData<String?>()
    val errorHappened: LiveData<String?>
        get() = _errorHappened

    private val _navigateToSignUp = MutableLiveData<Boolean>(false)
    val navigateToSignUp: LiveData<Boolean>
        get() = _navigateToSignUp

    private val _navigateToSignIn = MutableLiveData<Boolean>(false)
    val navigateToSignIn: LiveData<Boolean>
        get() = _navigateToSignIn

    private val _navigateToUser = MutableLiveData<Boolean>(false)
    val navigateToUser: LiveData<Boolean>
        get() = _navigateToUser

    private lateinit var notesCollection: DatabaseReference

    // init block to setup firebase Authentication and call the function that initializes database
    init {
        auth = Firebase.auth
        if (noteId.trim() == "") {
            note.value = Note()
        }
        _notes.value = mutableListOf<Note>()
        initializeTheDatabaseReference()
    }

    /**
     * initializeTheDatabaseReference()
     * initialize the Firebase Realtime Database and get the reference
     * addValueEventListener is always looking for any changes in the Database and adding notes if needed
     * if there is an error somewhere, it logs the failure message and cancels the post.
     */
    fun initializeTheDatabaseReference() {
        val database = Firebase.database
        notesCollection = database
            .getReference("notes")

        notesCollection.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var notesList : ArrayList<Note> = ArrayList()
                for (noteSnapshot in dataSnapshot.children) {
                    // TODO: handle the post
                    var note = noteSnapshot.getValue<Note>()
                    note?.noteId = noteSnapshot.key!!
                    notesList.add(note!!)
                }
                _notes.value = notesList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.v("FAIL", "This Post has Failed.")
            }
        })
    }

    // get all notes
    fun getAll() : LiveData<List<Note>> {
        return notes
    }

    // update note and navigate back to main screen
    fun updateNote() {
        if (noteId.trim() == "") {
            notesCollection.push().setValue(note.value)
        } else {
            notesCollection.child(noteId).setValue(note.value)
        }
        _navigateToList.value = true
    }

    // delete note and navigate back to main screen
    fun deleteNote(noteId: String) {
        notesCollection.child(noteId).removeValue()
        _navigateToList.value = true
    }
    // when note is clicked, navigate to the Edit Note screen
    fun onNoteClicked(selectedNote: Note) {
        _navigateToNote.value = selectedNote.noteId
        noteId = selectedNote.noteId
        note.value = selectedNote
    }

    // when new note button is clicked, go to Edit Note screen with a blank note template
    fun onNewNoteClicked() {
        _navigateToNote.value = ""
        noteId = ""
        note.value = Note()
    }

    // after note is navigated, make its value null
    fun onNoteNavigated() {
        _navigateToNote.value = null
    }

    // after user has navigated back to home screen, make _navigateToList false
    fun onNavigatedToList() {
        _navigateToList.value = false
    }

    // when user navigates to home screen, make _navigateToList true
    fun navigateToList() {
        _navigateToList.value = true
    }

    // when user navigates to sign up page, make _navigateToSignUp true
    fun navigateToSignUp() {
        _navigateToSignUp.value = true
    }

    // after user has navigated to sign up page, make _navigateToSignUp false
    fun onNavigatedToSignUp() {
        _navigateToSignUp.value = false
    }

    // when user navigates to sign in page, make _navigateToSignIn true
    fun navigateToSignIn() {
        _navigateToSignIn.value = true
    }

    // after user has navigates to sign in page, make _navigateToSignIn false
    fun onNavigatedToSignIn() {
        _navigateToSignIn.value = false
    }

    // when user navigates to user profile, make _navigateToUser true
    fun navigateToUser() {
        _navigateToUser.value = true
    }

    // after user has navigated to user profile, make _navigateToUser false
    fun onNavigatedToUser() {
        _navigateToUser.value = false
    }

    /**
     * signIn()
     *
     * if email and password fields are empty, return _errorHappened
     * else
     * sign in with email/pw using in-built Firebase function
     */
    fun signIn() {
        if (user.email.isEmpty() || user.password.isEmpty()) {
            _errorHappened.value = "Email and password cannot be empty."
            return
        }
        auth.signInWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
            if (it.isSuccessful) {
                initializeTheDatabaseReference()
                _navigateToList.value = true
            } else {
                _errorHappened.value = it.exception?.message
            }
        }
    }

    /**
     * signUp()
     *
     * if email and password fields are empty or password and verify don't match, return _errorHappened
     * else
     * create user with email/pw using in-built Firebase function
     */
    fun signUp() {
        if (user.email.isEmpty() || user.password.isEmpty()) {
            _errorHappened.value = "Email and password cannot be empty."
            return
        }
        if (user.password != verifyPassword) {
            _errorHappened.value = "Password and verify do not match."
            return
        }
        auth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
            if (it.isSuccessful) {
                _navigateToSignIn.value = true
            } else {
                _errorHappened.value = it.exception?.message
            }
        }
    }

    // sign out using in-built Firebase function and navigate to sign in page
    fun signOut() {
        auth.signOut()
        _navigateToSignIn.value = true
    }

    // get current user from Firebase
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}
package com.example.c323_project6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.c323_project6.databinding.FragmentMainScreenBinding
class MainFragment : Fragment() {
    // initialize binding and add non-null asserted calls
    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        val view = binding.root

        // initialize application and dao
        val application = requireNotNull(this.activity).application
        val dao = NoteDatabase.getInstance(application).noteDao

        // initialize the ViewModelFactory and get the viewModel, then set viewModel and lifecycleOwner
        val viewModelFactory = NotesViewModelFactory(dao)
        val viewModel = ViewModelProvider(
            this, viewModelFactory).get(NotesViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        /**
         * noteClicked : run onNoteClicked from viewModel using the noteId of the note that was clicked
         */
        fun noteClicked (noteId : Long) {
            viewModel.onNoteClicked(noteId)
        }
        /**
         * yesPressed : if yes is pressed on comfirmation dialog, run deleteNote from viewModel
         * using noteId whose delButton was pressed
         */
        fun yesPressed(noteId : Long) {
            binding.viewModel?.deleteNote(noteId)
        }
        /**
         * deleteClicked : create a confirmation dialog to delete note whose delButton was pressed
         */
        fun deleteClicked (noteId : Long) {
            ConfirmDeleteDialogFragment(noteId,::yesPressed).show(childFragmentManager,
                ConfirmDeleteDialogFragment.TAG)
        }

        // initialize and set adapter
        val adapter = NoteItemAdapter(::noteClicked,::deleteClicked)
        binding.notesList.adapter = adapter

        // when a notes item is observed, submitList of notes to the adapter
        viewModel.notes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        // navigate to EditNote Fragment
        viewModel.navigateToNote.observe(viewLifecycleOwner, Observer { noteId ->
            noteId?.let {
                val action = MainFragmentDirections.actionMainFragmentToEditNoteFragment(noteId)
                this.findNavController().navigate(action)
                viewModel.onNoteNavigated()
            }
        })
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
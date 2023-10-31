package com.example.c323_project6

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.c323_project6.databinding.FragmentNoteBinding

class EditNoteFragment : Fragment() {
    // initialize binding and add non-null asserted calls
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //inflate layout and get noteId from MainFragment
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        val view = binding.root
        val noteId = EditNoteFragmentArgs.fromBundle(requireArguments()).noteId

        val viewModel : NotesViewModel by activityViewModels()
        viewModel.noteId = noteId
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // navigate back to MainFragment
        viewModel.navigateToList.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                view.findNavController()
                    .navigate(R.id.action_editNoteFragment_to_mainFragment)
                viewModel.onNavigatedToList()
            }
        })

        // navigate to UserScreen Fragment
        viewModel.navigateToUser.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                view.findNavController()
                    .navigate(R.id.action_editNoteFragment_to_userScreen)
                viewModel.onNavigatedToUser()
            }
        })

        fun yesPressed(noteId : String) {
            //TODO: delete the task with id = noteId
            viewModel.deleteNote(noteId)
        }

        binding.delNoteFragment.setOnClickListener{
            ConfirmDeleteDialogFragment(noteId,::yesPressed).show(childFragmentManager,
                ConfirmDeleteDialogFragment.TAG)
        }
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
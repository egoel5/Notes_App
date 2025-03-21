package com.example.c323_project6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.c323_project6.EditNoteFragmentArgs
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

        // initialize application and dao
        val application = requireNotNull(this.activity).application
        val dao = NoteDatabase.getInstance(application).noteDao

        // initialize the ViewModelFactory and get the viewModel, then set viewModel and lifecycleOwner
        val viewModelFactory = EditNoteViewModelFactory(noteId, dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(EditNoteViewModel::class.java)

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
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
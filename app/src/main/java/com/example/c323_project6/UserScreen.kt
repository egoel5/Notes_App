package com.example.c323_project6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.c323_project6.databinding.FragmentUserScreenBinding

/**
 * A simple [Fragment] subclass.
 * Use the [UserScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserScreen : Fragment() {
    val TAG = "SignInFragment"
    private var _binding: FragmentUserScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        val viewModel: NotesViewModel by activityViewModels()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.navigateToSignIn.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                view.findNavController()
                    .navigate(R.id.action_userScreen_to_signInFragment)
                viewModel.onNavigatedToSignIn()
            }
        })

        // navigate back to home screen
        viewModel.navigateToList.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                view.findNavController()
                    .navigate(R.id.action_userScreen_to_mainFragment)
                viewModel.onNavigatedToList()
            }
        })
        return view
    }
}
package com.example.c323_project6

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment

class ConfirmDeleteDialogFragment(val taskId : String,val clickListener: (taskId: String) -> Unit) : DialogFragment() {
    val TAG = "ConfirmDeleteDialogFragment"
    interface myClickListener {
        fun yesPressed()
    }

    var listener: myClickListener? = null

    /**
     * Initialize a Fragment that will act as a Delete Confirmation
     * Set message, positiveButton's text, and negative button's text
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.delete_confirmation))
            .setPositiveButton("Yes") { _,_ -> clickListener(taskId)}
            .setNegativeButton("No") { _,_ -> }
            .create()

    companion object {
        const val TAG = "ConfirmDeleteDialogFragment"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as myClickListener
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }
}
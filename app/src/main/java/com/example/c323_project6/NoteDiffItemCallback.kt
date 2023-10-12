package com.example.c323_project6

import androidx.recyclerview.widget.DiffUtil

/**
 * Compare items and return if they are same or aren't
 * Compare content and return if the content in both items is same or not
 */
class NoteDiffItemCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note)
            = (oldItem.noteId == newItem.noteId)
    override fun areContentsTheSame(oldItem: Note, newItem: Note) = (oldItem == newItem)
}
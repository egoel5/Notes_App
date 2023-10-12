package com.example.c323_project6

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    // initialize noteDao
    abstract val noteDao: NoteDao
    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        /**
         * get specific instance from NoteDatabase
         */
        fun getInstance(context: Context): NoteDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java,
                        "notes_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
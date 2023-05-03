package com.example.notes.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notes.DAO.NotesDao;
import com.example.notes.Model.Notes;

@Database(entities= Notes.class, version = 1)
public abstract class NotesDatabase extends RoomDatabase {
     private static NotesDatabase instance;
     public abstract NotesDao notesDao();
     public static synchronized NotesDatabase getInstance(Context context){
         if(instance==null){
             instance = Room.databaseBuilder(
                     context.getApplicationContext(),
                     NotesDatabase.class,"notes_database")
                     .fallbackToDestructiveMigration()
                     .build();
         }
         return instance;
     }
}

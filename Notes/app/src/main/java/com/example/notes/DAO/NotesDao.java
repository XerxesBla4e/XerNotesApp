package com.example.notes.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notes.Model.Notes;

import java.util.List;

@Dao
public interface NotesDao {
    @Insert
    public void insert(Notes notes);
    @Update
    public void update(Notes notes);
    @Delete
    public void delete(Notes notes);
    @Query("SELECT * FROM my_notes")
    public LiveData<List<Notes>>getAllData();
}

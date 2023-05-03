package com.example.notes.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.notes.DAO.NotesDao;
import com.example.notes.Database.NotesDatabase;
import com.example.notes.Model.Notes;

import java.util.List;

public class NotesRepo {
    private NotesDao notesDao;
    private LiveData<List<Notes>> noteslist;

    public NotesRepo(Application application) {
        NotesDatabase notesDatabase = NotesDatabase.getInstance(application);
        notesDao = notesDatabase.notesDao();
        noteslist = notesDao.getAllData();
    }

    public void insertData(Notes notes) {
        new InsertTask(notesDao).execute(notes);
    }

    public void updateData(Notes notes) {
        new UpdateTask(notesDao).execute(notes);
    }

    public void deleteData(Notes notes) {
        new DeleteTask(notesDao).execute(notes);
    }

    public LiveData<List<Notes>> getAllData() {
        return noteslist;
    }

    private static class InsertTask extends AsyncTask<Notes, Void, Void> {
        private NotesDao notesDao;

        public InsertTask(NotesDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            notesDao.insert(notes[0]);
            return null;
        }
    }
    private static class UpdateTask extends AsyncTask<Notes, Void, Void> {
        private NotesDao notesDao;

        public UpdateTask(NotesDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            notesDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteTask extends AsyncTask<Notes, Void, Void> {
        private NotesDao notesDao;

        public DeleteTask(NotesDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            notesDao.delete(notes[0]);
            return null;
        }
    }
}

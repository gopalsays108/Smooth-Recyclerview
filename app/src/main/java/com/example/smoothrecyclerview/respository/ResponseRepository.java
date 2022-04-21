package com.example.smoothrecyclerview.respository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.smoothrecyclerview.dao.ResponseDao;
import com.example.smoothrecyclerview.database.ResponseDatabase;
import com.example.smoothrecyclerview.modal.ResponseModal;

import java.util.List;

public class ResponseRepository {
    private ResponseDatabase crewDatabase;

    private LiveData<List<ResponseModal>> getAllCrews;

    public ResponseRepository(Application application) {
        crewDatabase = ResponseDatabase.getInstance( application );
        getAllCrews = crewDatabase.responseDao().getAllCrews();
    }

    public LiveData<List<ResponseModal>> getAllResponse() {
        return getAllCrews;
    }

    public void deleteALl() {
        new DeleteAll( crewDatabase ).execute();
    }

    public void insertResponse(List<ResponseModal> crewsModals) {
        new InsertAsyncTask( crewDatabase ).execute( crewsModals );
    }

    static class DeleteAll extends AsyncTask<Void, Void, Void> {

        private ResponseDao crewsDao;

        DeleteAll(ResponseDatabase crewDatabase) {
            crewsDao = crewDatabase.responseDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            crewsDao.deleteAll();
            return null;
        }
    }

    static class InsertAsyncTask extends AsyncTask<List<ResponseModal>, Void, Void> {

        private ResponseDao crewsDao;

        InsertAsyncTask(ResponseDatabase crewDatabase) {
            crewsDao = crewDatabase.responseDao();
        }

        @Override
        protected Void doInBackground(List<ResponseModal>... lists) {
            Log.d( "TAG", "insertCrew: bg " + lists[0] );
            crewsDao.insertResponse( lists[0] );
            return null;
        }
    }
}


package com.example.smoothrecyclerview.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.smoothrecyclerview.dao.ResponseDao;
import com.example.smoothrecyclerview.modal.ResponseModal;

@Database(entities = {ResponseModal.class}, version = 1)
public abstract class ResponseDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "responseDatabase";

    public abstract ResponseDao responseDao();

    private static volatile ResponseDatabase INSTANCE;

    public static ResponseDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ResponseDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder( context,
                            ResponseDatabase.class,
                            DATABASE_NAME )
                            .addCallback( callback )
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate( db );
            new PopulateAsync( INSTANCE );
        }
    };

    static class PopulateAsync extends AsyncTask<Void, Void, Void> {

        private ResponseDao responseDao;

        PopulateAsync(ResponseDatabase responseDatabase) {
            responseDao = responseDatabase.responseDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            responseDao.deleteAll();
            return null;
        }
    }
}

package com.example.smoothrecyclerview.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.smoothrecyclerview.modal.ResponseModal;

import java.util.List;

@Dao
public interface ResponseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertResponse(List<ResponseModal> responseList);


    @Query( "SELECT * FROM response_list" )
    LiveData<List<ResponseModal>> getAllCrews();

    @Query( "DELETE FROM response_list " )
    void deleteAll();
}

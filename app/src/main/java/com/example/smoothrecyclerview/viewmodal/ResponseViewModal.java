package com.example.smoothrecyclerview.viewmodal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.smoothrecyclerview.modal.ResponseModal;
import com.example.smoothrecyclerview.respository.ResponseRepository;

import java.util.List;

public class ResponseViewModal extends AndroidViewModel {
    private ResponseRepository responseRepository;
    private LiveData<List<ResponseModal>> getAllResponse;
    private String id = "";

    public ResponseViewModal(@NonNull Application application, String id) {
        super(application);
        this.id = id;
    }

    public ResponseViewModal(@NonNull Application application) {
        super(application);
        responseRepository = new ResponseRepository(application);
        getAllResponse = responseRepository.getAllResponse();
    }

    public void insertCrew(List<ResponseModal> modals) {
        responseRepository.insertResponse(modals);
    }

    public LiveData<List<ResponseModal>> getAllResponse() {
        return getAllResponse;
    }
}


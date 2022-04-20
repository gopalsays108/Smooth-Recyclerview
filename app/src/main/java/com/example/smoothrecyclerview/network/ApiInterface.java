package com.example.smoothrecyclerview.network;


import com.example.smoothrecyclerview.modal.ResponseModal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("easygautam/data/users")
    Call<List<ResponseModal>> getAllTeachers();
}

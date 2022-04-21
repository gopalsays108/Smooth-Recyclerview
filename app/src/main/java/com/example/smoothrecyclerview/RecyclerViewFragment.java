package com.example.smoothrecyclerview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smoothrecyclerview.adapter.ResponseRecyclerAdapter;
import com.example.smoothrecyclerview.modal.ResponseModal;
import com.example.smoothrecyclerview.network.ApiInterface;
import com.example.smoothrecyclerview.respository.ResponseRepository;
import com.example.smoothrecyclerview.viewmodal.ResponseViewModal;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerViewFragment extends Fragment {

    private static final String TAG = RecyclerViewFragment.class.getSimpleName();
    private ResponseRecyclerAdapter recyclerAdapter;
    private List<ResponseModal> responseModalArrayList;
    private RecyclerView responseRv;
    private ResponseRepository responseRepository;
    private ResponseViewModal responseViewModal;


    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        responseModalArrayList = new ArrayList<>();
        responseRv = view.findViewById(R.id.response_rv);
        responseRv.setLayoutManager(new LinearLayoutManager(getContext()));

        responseViewModal = new ViewModelProvider(this)
                .get(ResponseViewModal.class);
        responseRepository = new ResponseRepository(responseViewModal.getApplication());

        recyclerAdapter = new ResponseRecyclerAdapter(getContext(), responseModalArrayList);
        responseRv.setAdapter(recyclerAdapter);
        responseRv.setHasFixedSize(true);

        responseViewModal.getAllResponse().observe(getViewLifecycleOwner(), new Observer<List<ResponseModal>>() {
            @Override
            public void onChanged(List<ResponseModal> crewsModals) {
                recyclerAdapter.getAllResponse(crewsModals);
                responseRv.setAdapter(recyclerAdapter);
            }
        });
        fetchDataFromApi();
        return view;
    }

    private void fetchDataFromApi() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<List<ResponseModal>> responseModalCall = apiInterface.getAllTeachers();
        responseModalCall.enqueue(new Callback<List<ResponseModal>>() {
            @Override
            public void onResponse(@NonNull Call<List<ResponseModal>> call, @NonNull Response<List<ResponseModal>> response) {
                if (call.isExecuted() && response.isSuccessful()) {
                    responseModalArrayList = response.body();
                    responseRepository.insertResponse(response.body());
                    Log.i(TAG, "onResponse: array list: " + responseModalArrayList);

                } else {
                    Log.i(TAG, "onResponse failed: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ResponseModal>> call, @NonNull Throwable t) {
                Log.i(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}
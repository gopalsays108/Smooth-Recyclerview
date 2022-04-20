package com.example.smoothrecyclerview;

import static com.example.smoothrecyclerview.utils.Constants.BASE_URL;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smoothrecyclerview.adapter.ResponseRecyclerAdapter;
import com.example.smoothrecyclerview.modal.ResponseModal;
import com.example.smoothrecyclerview.network.ApiInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;

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
        responseRv.setHasFixedSize(true);

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
                    Log.i(TAG, "onResponse: array list: " + responseModalArrayList);

                } else {
                    Log.i(TAG, "onResponse failed: " + response.errorBody());
                }
                initRecyclerView();
            }

            @Override
            public void onFailure(@NonNull Call<List<ResponseModal>> call, @NonNull Throwable t) {
                Log.i(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void initRecyclerView() {
        recyclerAdapter = new ResponseRecyclerAdapter(getContext(), responseModalArrayList);
        responseRv.setAdapter(recyclerAdapter);
        responseRv.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
package com.example.smoothrecyclerview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smoothrecyclerview.R;
import com.example.smoothrecyclerview.modal.ResponseModal;

import java.util.List;

public class ResponseRecyclerAdapter extends RecyclerView.Adapter<ResponseRecyclerAdapter.ViewHolder> {
    private final Context context;
    private final List<ResponseModal> responseModalList;

    public ResponseRecyclerAdapter(Context context, List<ResponseModal> responseModalList) {
        this.context = context;
        this.responseModalList = responseModalList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.response_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getNameTv().setText(responseModalList.get(position).getName());
        holder.getSubjectTv().setText(responseModalList.get(position).getSubjects().get(0));
        holder.getSchoolTv().setText(responseModalList.get(position).getQualification().get(0));
        String url = responseModalList.get(position).getProfileImage();
        holder.viewMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Smile 😃", Toast.LENGTH_SHORT).show();
            }
        });
        setUserImage(url, holder);
    }

    private void setUserImage(String url, ViewHolder holder) {
        Glide.with(context).load(url)
                .into(holder.getImage());
    }

    @Override
    public int getItemCount() {
        return responseModalList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTv;
        private final TextView subjectTv;
        private final TextView schoolTv;
        private final ImageView image;
        private final AppCompatButton viewMoreBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.titleTv);
            subjectTv = itemView.findViewById(R.id.subtitle_tv);
            schoolTv = itemView.findViewById(R.id.school_tv);
            image = itemView.findViewById(R.id.teacherIv);
            viewMoreBtn = itemView.findViewById(R.id.appCompatButton);
        }

        public TextView getNameTv() {
            return nameTv;
        }

        public AppCompatButton getViewMoreBtn() {
            return viewMoreBtn;
        }

        public TextView getSubjectTv() {
            return subjectTv;
        }

        public TextView getSchoolTv() {
            return schoolTv;
        }

        public ImageView getImage() {
            return image;
        }
    }
}

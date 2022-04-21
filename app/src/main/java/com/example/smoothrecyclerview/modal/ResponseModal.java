package com.example.smoothrecyclerview.modal;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.smoothrecyclerview.utils.Converter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Entity(tableName = "response_list")
//@Database(version = 1, entities = {Entity.class})
@TypeConverters(Converter.class)
public class ResponseModal implements Serializable {

    @PrimaryKey
    private int id;

    private String name;
    private List<String> subjects;
    private List<String> qualification;
    private String profileImage;
    private static final long serialVersionUID = 1L;

    public ResponseModal() {
    }

    public ResponseModal(int id, String name, List<String> subjects,
                         List<String> qualification, String profileImage) {
        this.id = id;
        this.name = name;
        this.subjects = subjects;
        this.qualification = qualification;
        this.profileImage = profileImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public List<String> getQualification() {
        return qualification;
    }

    public void setQualification(List<String> qualification) {
        this.qualification = qualification;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @NonNull
    @Override
    public String toString() {
        return "ResponseModal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subjects=" + subjects +
                ", qualification=" + qualification +
                ", profileImage='" + profileImage + '\'' +
                '}';
    }
}

package com.example.lamprini.ellinika.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * @author Lamprini Georgatsou
 */

@Entity(primaryKeys = {"groupName", "title"})
public class Lesson {

    // {Group x Title} define a lesson.

    @ForeignKey(entity = Group.class, parentColumns = "name", childColumns = "groupName")
    @NonNull
    private String groupName = "";

    @NonNull
    private String title = "";

    private String text;

    private List<Exercise> exercises;

    private String extras;

    public Lesson() { }

    @Ignore
    public Lesson(@NonNull String groupName, @NonNull String title) {

        this.groupName = groupName;
        this.title = title;

    }

    @Ignore
    public Lesson(@NonNull String title, String text, List<Exercise> exercises, String extras) {

        this.title = title;
        this.text = text;
        this.exercises = exercises;
        this.extras = extras;

    }

    @NonNull
    public String getTitle() { return title; }

    public void setTitle(@NonNull String title) { this.title = title; }

    @NonNull
    public String getGroupName() { return groupName; }

    public void setGroupName(@NonNull String groupName) { this.groupName = groupName; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public List<Exercise> getExercises() { return exercises; }

    public void setExercises(List<Exercise> exercises) { this.exercises = exercises; }

    public String getExtras() { return extras; }

    public void setExtras(String extras) { this.extras = extras; }

}

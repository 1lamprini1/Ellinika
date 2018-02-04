package com.example.lamprini.ellinika.db;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lamprini Georgatsou
 */

public abstract class Exercise {

    private int number;

    protected String text;

    protected List<String> solutions;

    // For GSON.
    public Exercise() { }

    public Exercise(String text) {

        this.text = text;
        this.solutions = new ArrayList<>();

    }

    public int getNumber() { return number; }

    public void setNumber(int number) { this.number = number; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public List<String> getSolutions() { return solutions; }

    public String getSolution(int i) { return solutions.get(i); }

    public void setSolutions(List<String> solutions) { this.solutions = solutions; }

    public void addSolution(String solution) { solutions.add(solution); }

}

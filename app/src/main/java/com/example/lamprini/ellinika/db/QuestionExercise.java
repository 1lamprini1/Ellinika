package com.example.lamprini.ellinika.db;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author Lamprini Georgatsou
 */

public class QuestionExercise extends Exercise implements Parcelable {

    public QuestionExercise(String text) {
        super(text);
    }

    protected QuestionExercise(Parcel parcel) {

        text = parcel.readString();
        solutions = new ArrayList<>();
        parcel.readList(solutions, Exercise.class.getClassLoader());

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(text);
        parcel.writeList(solutions);

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Exercise>() {

        @Override
        public QuestionExercise createFromParcel(Parcel parcel) {
            return new QuestionExercise(parcel);
        }

        @Override
        public QuestionExercise[] newArray(int size) {
            return new QuestionExercise[size];
        }

    };

}

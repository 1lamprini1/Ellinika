package com.example.lamprini.ellinika.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * @author Lamprini Georgatsou
 */

@Entity
public class Dict {

    // A dictionary entry.

    @PrimaryKey
    @NonNull
    private String word = "";

    private String meaning;

    public Dict() { }

    @Ignore
    public Dict(@NonNull String word) { this.word = word; }

    @Ignore
    public Dict(@NonNull String word, String meaning) {

        this.word = word;
        this.meaning = meaning;

    }

    @NonNull
    public String getWord() { return word; }

    public void setWord(@NonNull String word) { this.word = word; }

    public String getMeaning() { return meaning; }

    public void setMeaning(String meaning) { this.meaning = meaning; }

}

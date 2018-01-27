package com.example.lamprini.ellinika.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * @author Lamprini Georgatsou
 */

@Entity
public class Group {

    // This is a study group or class.

    @PrimaryKey
    @NonNull
    private String name = "";

    public Group() { }

    @Ignore
    public Group(@NonNull String name) { this.name = name; }

    @NonNull
    public String getName() { return name; }

    public void setName(@NonNull String name) { this.name = name; }

}

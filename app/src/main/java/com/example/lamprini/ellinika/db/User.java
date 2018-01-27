package com.example.lamprini.ellinika.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * @author Lamprini Georgatsou
 */

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    private String name;

    private String pass;

    public User(String name, String pass) {

        this.name = name;
        this.pass = pass;

    }

    public void setUid(int uid) { this.uid = uid; }

    public int getUid() { return uid; }

    public String getName() { return name; }

    public String getPass() { return pass; }

}

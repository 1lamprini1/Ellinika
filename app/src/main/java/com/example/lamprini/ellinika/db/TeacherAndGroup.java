package com.example.lamprini.ellinika.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * @author Lamprini Georgatsou
 */

@Entity
public class TeacherAndGroup {

    // Teachers can create, modify and view lessons of a group where they teach.
    // They can also modify the group to add / remove teachers or students.

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ForeignKey(entity = User.class, parentColumns = "uid", childColumns = "teacherId", onDelete = CASCADE, onUpdate = CASCADE)
    private int teacherId;

    @ForeignKey(entity = Group.class, parentColumns = "name", childColumns = "groupName", onDelete = CASCADE, onUpdate = CASCADE)
    private String groupName;

    public TeacherAndGroup() { }

    @Ignore
    public TeacherAndGroup(int teacherId, String groupName) {

        this.teacherId = teacherId;
        this.groupName = groupName;

    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getTeacherId() { return teacherId; }

    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }

    public String getGroupName() { return groupName; }

    public void setGroupName(String groupName) { this.groupName = groupName; }

}

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
public class StudentAndGroup {

    // Students can only view lessons of the group.

    @PrimaryKey
    private int id;

    @ForeignKey(entity = User.class, parentColumns = "uid", childColumns = "studentId", onDelete = CASCADE, onUpdate = CASCADE)
    private int studentId;

    @ForeignKey(entity = Group.class, parentColumns = "name", childColumns = "groupName", onDelete = CASCADE, onUpdate = CASCADE)
    private String groupName;

    public StudentAndGroup() { }

    @Ignore
    public StudentAndGroup(int studentId, String groupName) {

        this.studentId = studentId;
        this.groupName = groupName;

    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }

    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getGroupName() { return groupName; }

    public void setGroupName(String groupName) { this.groupName = groupName; }

}

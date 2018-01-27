package com.example.lamprini.ellinika.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * @author Lamprini Georgatsou
 */

@Dao
public interface StudentAndGroupDao {

    @Query("SELECT * FROM StudentAndGroup")
    List<StudentAndGroup> getStudentGroups();

    @Query("SELECT studentId FROM StudentAndGroup WHERE groupName LIKE :groupName")
    List<Integer> getStudentIds(String groupName);

    @Query("SELECT groupName FROM StudentAndGroup WHERE studentId LIKE :studentId")
    List<String> getGroups(Integer studentId);

    @Insert
    void createStudentGroup(StudentAndGroup studentAndGroup);

    @Delete
    void removeStudentGroup(StudentAndGroup studentAndGroup);

    @Delete
    void removeStudentGroups(List<StudentAndGroup> studentsAndGroups);

}

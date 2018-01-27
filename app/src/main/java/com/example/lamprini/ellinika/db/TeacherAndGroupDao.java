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
public interface TeacherAndGroupDao {

    @Query("SELECT * FROM Teacherandgroup")
    List<TeacherAndGroup> getTeacherGroups();

    @Query("SELECT teacherId FROM TeacherAndGroup WHERE groupName LIKE :name")
    List<Integer> getTeacherIds(String name);

    @Query("SELECT groupName FROM TeacherAndGroup WHERE teacherId LIKE :id")
    List<String> getGroups(Integer id);

    @Insert
    void createTeacherAndGroup(TeacherAndGroup teacherAndGroup);

    @Delete
    void removeTeacherAndGroup(TeacherAndGroup teacherAndGroup);

    @Delete
    void removeTeacherGroups(List<TeacherAndGroup> teachersAndGroups);

}

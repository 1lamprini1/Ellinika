package com.example.lamprini.ellinika.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * @author Lamprini Georgatsou
 */

@Dao
public interface LessonDao {

    @Query("SELECT * FROM Lesson")
    List<Lesson> getLessons();

    @Query("SELECT * FROM Lesson WHERE groupName LIKE :group")
    List<Lesson> getLessonsForGroup(String group);

    @Query("SELECT title FROM Lesson WHERE groupName LIKE :group")
    List<String> getLessonTitlesForGroup(String group);

    @Query("SELECT * FROM Lesson WHERE groupName LIKE :group AND title LIKE :title")
    Lesson getLessonFromTitle(String group, String title);

    @Insert
    void createLesson(Lesson lesson);

    @Update
    void updateLesson(Lesson lesson);

    @Delete
    void deleteLesson(Lesson lesson);

    @Delete
    void deleteLessons(List<Lesson> lessons);

}

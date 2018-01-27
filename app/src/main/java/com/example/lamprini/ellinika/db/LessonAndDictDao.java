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
public interface LessonAndDictDao {

    @Query("SELECT dictWord FROM LessonAndDict WHERE lessonGroup LIKE :group AND lessonTitle LIKE :lesson")
    List<String> getLessonDict(String group, String lesson);

    @Insert
    void insertLessonDict(LessonAndDict lessonAndDict);

    @Update
    void updateLessonDict(LessonAndDict lessonAndDict);

    @Delete
    void deleteLessonDict(LessonAndDict lessonAndDict);

    @Delete
    void deleteLessonDicts(List<LessonAndDict> lessonsAndDicts);

}

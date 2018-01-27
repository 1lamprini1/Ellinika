package com.example.lamprini.ellinika.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * @author Lamprini Georgatsou
 */

@Entity(primaryKeys = {"lessonGroup", "lessonTitle"})
public class LessonAndDict {

    @NonNull
    @ForeignKey(entity = Lesson.class, parentColumns = "group", childColumns = "lessonGroup", onDelete = CASCADE, onUpdate = CASCADE)
    private String lessonGroup = "";

    @NonNull
    @ForeignKey(entity = Lesson.class, parentColumns = "title", childColumns = "lessonTitle", onDelete = CASCADE, onUpdate = CASCADE)
    private String lessonTitle = "";

    private String dictWord;

    @NonNull
    public String getLessonGroup() { return lessonGroup; }

    public void setLessonGroup(@NonNull String lessonGroup) { this.lessonGroup = lessonGroup; }

    @NonNull
    public String getLessonTitle() { return lessonTitle; }

    public void setLessonTitle(@NonNull String lessonTitle) { this.lessonTitle = lessonTitle; }

    public String getDictWord() { return dictWord; }

    public void setDictWord(String dictWord) { this.dictWord = dictWord; }

}

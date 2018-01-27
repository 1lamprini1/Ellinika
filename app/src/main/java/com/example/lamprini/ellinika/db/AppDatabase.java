package com.example.lamprini.ellinika.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.lamprini.ellinika.db.GroupDao.Converters;

/**
 * @author Lamprini Georgatsou
 */

@Database(entities = {User.class, Group.class, TeacherAndGroup.class,
        StudentAndGroup.class, Lesson.class, Dict.class, LessonAndDict.class}, version = 5)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract UserDao userDao();

    public abstract GroupDao groupDao();

    public abstract TeacherAndGroupDao teacherAndGroupDao();

    public abstract StudentAndGroupDao studentAndGroupDao();

    public abstract LessonDao lessonDao();

    public abstract DictDao dictDao();

    public abstract LessonAndDictDao lessonAndDictDao();

    public static AppDatabase getInstance(Context context) {

        if (instance == null) instance = Room
                .databaseBuilder(context, AppDatabase.class, "Lessons")
                .fallbackToDestructiveMigration()
                .build();
        return instance;

    }

    public static void destroyInstance() { instance = null; }

}

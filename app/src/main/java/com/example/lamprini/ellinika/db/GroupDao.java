package com.example.lamprini.ellinika.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.Update;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Lamprini Georgatsou
 */

@Dao
public interface GroupDao {

    @Query("SELECT * FROM `Group`")
    List<Group> getGroups();

    @Query("SELECT * FROM `Group` WHERE name LIKE :name")
    Group getGroupFromName(String name);

    @Insert
    void createGroup(Group group);

    @Update
    void updateGroup(Group group);

    @Delete
    void deleteGroup(Group group);

    @Delete
    void deleteGroups(List<Group> groups);

    class Converters {
//
//        @TypeConverter
//        public static List<Integer> fromInteger(String value) {
//
//            Type listType = new TypeToken<List<Integer>>(){}.getType();
//            return new Gson().fromJson(value, listType);
//
//        }
//
//        @TypeConverter
//        public static String fromIntegerList(List<Integer> list) {
//
//            Gson gson = new Gson();
//            return gson.toJson(list);
//
//        }
//
//        @TypeConverter
//        public static List<Lesson> fromLessonString(String value) {
//
//            Type listType = new TypeToken<List<Lesson>>(){}.getType();
//            return new Gson().fromJson(value, listType);
//
//        }
//
//        @TypeConverter
//        public static String fromLessonList(List<Lesson> list) {
//
//            Gson gson = new Gson();
//            return gson.toJson(list);
//
//        }

        @TypeConverter
        public static List<Exercise> fromExerciseString(String value) {

            Type listType = new TypeToken<List<Exercise>>(){}.getType();
            return new Gson().fromJson(value, listType);

        }

        @TypeConverter
        public static String fromExerciseList(List<Exercise> list) {

            Gson gson = new Gson();
            return gson.toJson(list);

        }
//
//        @TypeConverter
//        public static List<Dict> fromDictString(String value) {
//
//            Type listType = new TypeToken<List<Dict>>(){}.getType();
//            return new Gson().fromJson(value, listType);
//
//        }
//
//        @TypeConverter
//        public static String fromDictList(List<Dict> list) {
//
//            Gson gson = new Gson();
//            return gson.toJson(list);
//
//        }

    }

}

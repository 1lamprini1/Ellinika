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
public interface DictDao {

    @Query("SELECT * FROM Dict")
    List<Dict> getDict();

    @Query("SELECT * FROM Dict WHERE word LIKE :word")
    Dict getWord(String word);

    @Insert
    void createWord(Dict dict);

    @Update
    void updateWord(Dict dict);

    @Delete
    void removeWord(Dict dict);

    @Delete
    void removeDicts(List<Dict> words);

}

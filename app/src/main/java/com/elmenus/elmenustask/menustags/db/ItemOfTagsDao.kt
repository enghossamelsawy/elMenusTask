package com.elmenus.elmenustask.menustags.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elmenus.elmenustask.menustags.data.ItemOfTags


@Dao
interface ItemOfTagsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itemsOfTagsDao: List<ItemOfTags>)


    @Query("SELECT * FROM itemOfTags WHERE (tagName LIKE :queryString)  ORDER BY tagName ASC")
    suspend fun tagsByName(queryString: String): List<ItemOfTags>
}

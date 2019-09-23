package com.elmenus.elmenustask.menustags.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elmenus.elmenustask.menustags.data.TagObject

@Dao
interface TagsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tags: List<TagObject>)

    @Query("SELECT * FROM tags ORDER BY tagName ASC")
    fun tagsByName(): DataSource.Factory<Int, TagObject>
}

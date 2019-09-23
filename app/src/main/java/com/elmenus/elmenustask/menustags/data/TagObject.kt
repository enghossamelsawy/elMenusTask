package com.elmenus.elmenustask.menustags.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tags")
data class TagObject(
    @field:SerializedName("photoURL")
    var photoURL: String,
    @PrimaryKey @field:SerializedName("tagName")
    var tagName: String
)



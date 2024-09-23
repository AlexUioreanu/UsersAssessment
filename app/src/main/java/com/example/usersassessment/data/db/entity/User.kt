package com.example.usersassessment.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "nick_name")
    val nickName: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String
)

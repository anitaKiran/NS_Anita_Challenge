package com.nordsecurity.test

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ServersModelItem")
data class ServersModelItem(
    val distance: Int,
    val name: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)
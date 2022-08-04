package com.nordsecurity.test

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ServersModelItem::class], version = 1,exportSchema = false)
abstract class NordSecurityDatabase: RoomDatabase() {
    abstract fun getNordDao(): NordDao
}
package com.nordsecurity.test

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(arrayListNord: ArrayList<ServersModelItem>)

    @Query("SELECT * FROM ServersModelItem")
    suspend fun getList(): List<ServersModelItem>

    @Query("DELETE FROM ServersModelItem")
    suspend fun deleteAll()

}
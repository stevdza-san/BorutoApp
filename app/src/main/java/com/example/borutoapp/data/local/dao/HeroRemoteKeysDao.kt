package com.example.borutoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.borutoapp.domain.model.HeroRemoteKeys

@Dao
interface HeroRemoteKeysDao {

    @Query("SELECT * FROM hero_remote_keys_table WHERE id = :heroId")
    suspend fun getRemoteKeys(heroId: Int): HeroRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(heroRemoteKeys: List<HeroRemoteKeys>)

    @Query("DELETE FROM hero_remote_keys_table")
    suspend fun deleteAllRemoteKeys()

}
package com.part.sportpartapp.sportList.data.local.sport

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface SportDao {
    @Upsert
    suspend fun upsertSportList(sportList: List<SportEntity>)

    @Query("SELECT * FROM SportEntity")
    suspend fun getSportList(): List<SportEntity>

    @Query("SELECT * FROM SportEntity WHERE id=:id")
    suspend fun getSportById(id: Int): SportEntity

    @Query("SELECT * FROM SportEntity WHERE recommended=true")
    suspend fun getSportListRecommended(): List<SportEntity>

    @Query("SELECT * FROM SportEntity WHERE is_free=1")
    suspend fun getSportListFree(): List<SportEntity>

    @Query("SELECT * FROM SportEntity WHERE type=:type")
    suspend fun getSportListByType(type: Int): List<SportEntity>

    @Query("UPDATE SportEntity SET bookmark = CASE WHEN bookmark = 0 THEN 1 ELSE 0 END WHERE id = :id")
    suspend fun setBookmark(id: Int)

    @Query("SELECT * FROM SportEntity WHERE bookmark=1")
    suspend fun getSportListBookmark(): List<SportEntity>


}
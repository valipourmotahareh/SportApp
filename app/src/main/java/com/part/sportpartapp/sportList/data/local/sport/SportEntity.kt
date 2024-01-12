package com.part.sportpartapp.sportList.data.local.sport

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SportEntity(
    @PrimaryKey
    val id: Int,
    val description: String,
    val image: String,
    val is_free: Boolean,
    val order: Int,
    val recommended: Boolean,
    val title: String,
    val type: Int,
    val bookmark: Boolean
)
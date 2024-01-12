package com.part.sportpartapp.sportList.data.remote.respond

data class SportListDto(
    val description: String,
    val id: Int,
    val image: String,
    val is_free: Boolean,
    val order: Int,
    val recommended: Boolean,
    val title: String,
    val type: Int,
    val bookmark: Boolean
)
package com.part.sportpartapp.sportList.domain.model

data class Sport(
    val id: Int,
    val description: String,
    val image: String,
    val is_free: Boolean,
    val order: Int,
    val recommended: Boolean,
    val title: String,
    val type: Int,
    var bookmark: Boolean
)
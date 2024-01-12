package com.part.sportpartapp.sportList.presentation

import com.part.sportpartapp.sportList.domain.model.Sport

data class SportListState(
    val isLoading: Boolean = false,
    val SportListPage: Int = 1,
    val BookmarkSportListPage: Int = 1,
    val isCurrentSportListPage: Boolean = true,
    val SportList: List<Sport> = emptyList(),
)
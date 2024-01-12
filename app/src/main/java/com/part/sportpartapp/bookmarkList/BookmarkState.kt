package com.part.sportpartapp.bookmarkList

import com.part.sportpartapp.sportList.domain.model.Sport

data class BookmarkState(
    val isLoading: Boolean = false,
    val setBookmark: Boolean? = false,
    val BookmarkSportList: List<Sport> = emptyList(),

    )
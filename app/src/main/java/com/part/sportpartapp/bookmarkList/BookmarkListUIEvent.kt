package com.part.sportpartapp.bookmarkList
sealed interface BookmarkListUIEvent {
    object ShowListBookmark : BookmarkListUIEvent
    data class SetBookmark(val id: Int) : BookmarkListUIEvent

}
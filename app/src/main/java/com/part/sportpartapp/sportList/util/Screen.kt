package com.part.sportpartapp.sportList.util

sealed class Screen(val rout: String) {
    object Home : Screen("main")
    object BookmarkSportListPage : Screen("bookmarkSportList")
    object Details : Screen("details")
}
package com.part.sportpartapp.bookmarkList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.part.sportpartapp.sportList.presentation.components.SportItem

@Composable
fun BookmarkSportListPage(
    bookmarkState: BookmarkState,
    navHostController: NavHostController,
    onBookmarkEvent: (BookmarkListUIEvent) -> Unit
) {
    if (bookmarkState.BookmarkSportList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
        ) {
            items(bookmarkState.BookmarkSportList.size) { index ->

                SportItem(
                    sport = bookmarkState.BookmarkSportList[index],
                    navHostController = navHostController,
                    onBookmarkEvent = onBookmarkEvent
                )

                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }

}
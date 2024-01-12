package com.part.sportpartapp.core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.SportsBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.part.sportpartapp.R
import com.part.sportpartapp.bookmarkList.BookmarkListUIEvent
import com.part.sportpartapp.bookmarkList.BookmarkSportListPage
import com.part.sportpartapp.bookmarkList.BookmarksViewModel
import com.part.sportpartapp.sportList.presentation.SportListPage
import com.part.sportpartapp.sportList.presentation.SportListUIEvent
import com.part.sportpartapp.sportList.presentation.SportListViewModel
import com.part.sportpartapp.sportList.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val sportListViewModel = hiltViewModel<SportListViewModel>()
    val bookmarksViewModel = hiltViewModel<BookmarksViewModel>()
    val sportState = sportListViewModel.sportListState.collectAsState().value
    val bookmarkState = bookmarksViewModel.bookmarkState.collectAsState().value
    val bottomNavController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                bottomNavController = bottomNavController,
                onEvent = sportListViewModel::onEvent,
                onBookmarkEvent = bookmarksViewModel::onEvent

            )

        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (sportState.isCurrentSportListPage)
                            stringResource(R.string.Sportlist)
                        else
                            stringResource(R.string.Bookmarklist),
                        fontSize = 20.sp
                    )
                },
                modifier = Modifier.shadow(2.dp),
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    MaterialTheme.colorScheme.inverseOnSurface
                )
            )

        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(navController = bottomNavController, startDestination = Screen.Home.rout) {
                composable(Screen.Home.rout) {
                   SportListPage(sportListState = sportState, navHostController = navController,
                       onEvent = sportListViewModel::onEvent,
                       onBookmarkEvent =bookmarksViewModel::onEvent )
                }

                composable(Screen.BookmarkSportListPage.rout) {
                    BookmarkSportListPage(bookmarkState = bookmarkState, navHostController = navController,
                        onBookmarkEvent = bookmarksViewModel::onEvent,)
                }
            }

        }
    }
}

@Composable
fun BottomNavigationBar(
    bottomNavController: NavHostController,
    onEvent: (SportListUIEvent) -> Unit,
    onBookmarkEvent: (BookmarkListUIEvent) -> Unit
) {
    val items = listOf(
        BottomItem(
            title = stringResource(R.string.SportList),
            icon = Icons.Rounded.SportsBar,
        ),
        BottomItem(
            title = stringResource(R.string.Bookmark),
            icon = Icons.Rounded.Bookmark
        )
    )

    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }
    NavigationBar {
        Row(modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)) {
            items.forEachIndexed { index, bottomItem ->
                NavigationBarItem(selected = selected.intValue == index, onClick = {
                    selected.intValue = index
                    when (selected.intValue) {
                        0 -> {
                            onEvent(SportListUIEvent.ShowAllListSport)
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.Home.rout)

                        }
                        1 -> {
                            onBookmarkEvent(BookmarkListUIEvent.ShowListBookmark)
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.BookmarkSportListPage.rout)
                        }
                    }
                },
                    icon = {
                        Icon(
                            imageVector = bottomItem.icon, contentDescription = bottomItem.title,
                            tint = MaterialTheme.colorScheme.background
                        )
                    },
                    label = {
                        Text(text = bottomItem.title)
                    })

            }
        }
    }

}

data class BottomItem(
    val title: String,
    val icon: ImageVector
)
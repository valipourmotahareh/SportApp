package com.part.sportpartapp.sportList.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.part.sportpartapp.sportList.util.getAverageColor
import com.part.sportpartapp.bookmarkList.BookmarkListUIEvent
import com.part.sportpartapp.sportList.domain.model.Sport
import com.part.sportpartapp.sportList.util.Screen

@Composable
fun SportItem(
    sport: Sport,
    navHostController: NavHostController,
    onBookmarkEvent: (BookmarkListUIEvent) -> Unit,
) {
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(sport.image)
            .size(Size.ORIGINAL)
            .build()
    ).state
    val defaultColor = MaterialTheme.colorScheme.secondaryContainer
    var dominantColor by remember {
        mutableStateOf(defaultColor)
    }

    Column(
        modifier = Modifier
            .wrapContentWidth()
            .width(200.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .clickable {
                navHostController.navigate(Screen.Details.rout + "/${sport.id}")
            }
    ) {
        if (imageState is AsyncImagePainter.State.Error) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(70.dp),
                    imageVector = Icons.Rounded.ImageNotSupported,
                    contentDescription = sport.title
                )

            }
        }

        if (imageState is AsyncImagePainter.State.Success) {
            dominantColor =
                getAverageColor(imageBitmap = imageState.result.drawable.toBitmap().asImageBitmap())

            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp)
                    .height(130.dp)
                    .clip(RoundedCornerShape(20.dp)),
                painter = imageState.painter,
                contentDescription = sport.title,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier
                .padding(start = 26.dp, end = 8.dp)
                .align(alignment = Alignment.CenterHorizontally),
            text = sport.title,
            color = Color.Black,
            fontSize = 15.sp,
            maxLines = 1
        )

        IconButton(
            modifier = Modifier
                .size(48.dp)
                .padding(horizontal = 10.dp),
            onClick = {
                sport.bookmark = !sport.bookmark
                onBookmarkEvent(BookmarkListUIEvent.SetBookmark(sport.id))
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.Bookmark,
                tint = if (sport.bookmark) Color.Magenta else Color.Black,
                contentDescription = "Bookmark_sport",
            )
        }


    }
}
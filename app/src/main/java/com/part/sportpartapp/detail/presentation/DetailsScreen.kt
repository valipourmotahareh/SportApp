package com.part.sportpartapp.detail.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.part.sportpartapp.sportList.domain.model.selectFitnessCategoryByValue
import com.part.sportpartapp.R
import com.part.sportpartapp.sportList.domain.model.toDisplayString

@Composable
fun DetailScreen() {

    val detailsViewModel = hiltViewModel<DetailsViewModel>()
    val detailState = detailsViewModel.detailState.collectAsState().value

    val backDropImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(detailState.sport?.image)
            .size(Size.ORIGINAL)
            .build()
    ).state
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if (backDropImageState is AsyncImagePainter.State.Error) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(220.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(70.dp),
                    imageVector = Icons.Rounded.ImageNotSupported,
                    contentDescription = detailState.sport?.title
                )

            }
        }

        if (backDropImageState is AsyncImagePainter.State.Success) {

            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .height(220.dp),
                painter = backDropImageState.painter,
                contentDescription = detailState.sport?.title,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .height(240.dp)
            )
            {
                if (backDropImageState is AsyncImagePainter.State.Error) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(70.dp),
                            imageVector = Icons.Rounded.ImageNotSupported,
                            contentDescription = detailState.sport?.title
                        )

                    }
                }

                if (backDropImageState is AsyncImagePainter.State.Success) {

                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp)),
                        painter = backDropImageState.painter,
                        contentDescription = detailState.sport?.title,
                        contentScale = ContentScale.Crop
                    )
                }
            }

                    detailState.sport?.let { sportt ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 24.dp)
                        ) {
                            Text(
                                text = sportt.title,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconWithText(
                                    isTrue = sportt.is_free,
                                    trueIcon = Icons.Rounded.Check,
                                    falseIcon = Icons.Default.Clear,
                                    trueColor = Color.Green, // Adjust color as needed
                                    falseColor = Color.Red // Adjust color as needed
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = stringResource(R.string.free),
                                    fontSize = 16.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconWithText(
                                    isTrue = sportt.recommended,
                                    trueIcon = Icons.Default.Recommend,
                                    falseIcon = Icons.Default.Recommend,
                                    trueColor = Color.Magenta, // Adjust color as needed
                                    falseColor = Color.Gray // Adjust color as needed
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = stringResource(R.string.recommend),
                                    fontSize = 16.sp
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Icon(
                                    imageVector = Icons.Rounded.Category,
                                    contentDescription = "type",
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    modifier = Modifier.padding(start = 16.dp),
                                    text = selectFitnessCategoryByValue(sportt.type).toDisplayString(),
                                    fontSize = 16.sp
                                )
                            }

                        }
                    }
        }

        Spacer(modifier = Modifier.height(8.dp))

        detailState.sport?.let {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = it.description,
                fontSize = 16.sp,
            )
        }

    }
}

@Composable
fun IconWithText(
    isTrue: Boolean,
    trueIcon: ImageVector,
    falseIcon: ImageVector,
    trueColor: Color,
    falseColor: Color
) {
    val icon = if (isTrue) trueIcon else falseIcon
    val contentDescription = if (isTrue) "True" else "False"

    Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        tint = if (isTrue) trueColor else falseColor
    )
}
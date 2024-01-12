package com.part.sportpartapp.sportList.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.part.sportpartapp.sportList.domain.model.FitnessCategory
import com.part.sportpartapp.sportList.presentation.components.SportItem
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.part.sportpartapp.bookmarkList.BookmarkListUIEvent
import com.part.sportpartapp.sportList.domain.model.toDisplayString
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportListPage(
    sportListState: SportListState,
    navHostController: NavHostController,
    onEvent: (SportListUIEvent) -> Unit,
    onBookmarkEvent: (BookmarkListUIEvent) -> Unit
) {
  Column() {
      Row(modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Start){
          var isRecommendSelected by remember { mutableStateOf(false) }
          var isFreeSelected by remember { mutableStateOf(false) }

          FilterChip(
              modifier = Modifier.padding(horizontal = 6.dp),
              selected = isRecommendSelected,
              onClick = {
                  isRecommendSelected=!isRecommendSelected
                  isFreeSelected=false
                  onEvent(SportListUIEvent.Recommended)
              },
              label = { Text("Recomended") }
          )

          FilterChip(
              modifier = Modifier.padding(horizontal = 6.dp),
              selected = isFreeSelected,
              onClick = {
                  isFreeSelected=!isFreeSelected
                  isRecommendSelected=false
                  onEvent(SportListUIEvent.Free)
              },
              label = { Text("Free") }
          )
          Text("Type:", modifier = Modifier
              .align(Alignment.CenterVertically))
          var expanded by remember { mutableStateOf(false) }
          var selectedOptionText by remember { mutableStateOf(FitnessCategory.All.toDisplayString()) }
          Box (
              modifier = Modifier
                  .padding(horizontal = 8.dp) // Adjust the margin as needed
          ){
              ExposedDropdownMenuBox(
                  expanded = expanded,
                  modifier = Modifier
                      .background(MaterialTheme.colorScheme.background) // Set background color
                      .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.small) // Add border
                      .clip(MaterialTheme.shapes.small)
                      .height(45.dp)
                      .padding(0.dp),
                  onExpandedChange = {
                      expanded = !expanded
                  }
              ) {
                  TextField(
                      modifier = Modifier.menuAnchor().padding(0.dp),
                      readOnly = true,
                      value = selectedOptionText,
                      textStyle = TextStyle(fontSize = 14.sp),
                      onValueChange = { },
                      trailingIcon = {
                          ExposedDropdownMenuDefaults.TrailingIcon(
                              expanded = expanded
                          )
                      },
                      colors = ExposedDropdownMenuDefaults.textFieldColors(
                          focusedIndicatorColor = Color.Transparent,
                          unfocusedIndicatorColor = Color.Transparent,
                      )
                  )
                  ExposedDropdownMenu(
                      expanded = expanded,
                      onDismissRequest = {
                          expanded = false
                      },
                      modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(0.dp)
                  ) {
                      FitnessCategory.values().forEach {fitnessCategory ->
                          DropdownMenuItem(
                              onClick = {
                                  selectedOptionText = fitnessCategory.toDisplayString()
                                  expanded = false
                                  when (fitnessCategory) {
                                      FitnessCategory.HIGH_INTENSITY -> {
                                          onEvent(SportListUIEvent.SelectedType(1))
                                      }
                                      FitnessCategory.MIND_BODY -> {
                                          onEvent(SportListUIEvent.SelectedType(2))
                                      }
                                      FitnessCategory.FUNCTIONAL -> {
                                          onEvent(SportListUIEvent.SelectedType(3))
                                      }
                                      FitnessCategory.COMPREHENSIVE -> {
                                          onEvent(SportListUIEvent.SelectedType(4))
                                      }
                                      FitnessCategory.All ->{
                                          onEvent(SportListUIEvent.SelectedType(0))
                                      }
                                  }
                              },
                              text = {
                                  Text(text = fitnessCategory.toDisplayString())
                              }
                          )

                      }
                  }
              }
          }

      }

      if (sportListState.SportList.isEmpty()) {
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
              items(sportListState.SportList.size) { index ->

                  SportItem(
                      sport = sportListState.SportList[index],
                      navHostController = navHostController,
                      onBookmarkEvent =onBookmarkEvent,
                  )

                  Spacer(modifier = Modifier.height(16.dp))

              }
          }
      }
  }

}
package com.part.sportpartapp.detail.presentation

import com.part.sportpartapp.sportList.domain.model.Sport

data class DetailsState(
    val isLoading: Boolean = false,
    val sport: Sport? = null
)
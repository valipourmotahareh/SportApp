package com.part.sportpartapp.sportList.presentation

sealed interface SportListUIEvent {
    object ShowAllListSport : SportListUIEvent
    object Recommended : SportListUIEvent
    object Free : SportListUIEvent
    data class SelectedType(val type: Int) : SportListUIEvent

}
package com.part.sportpartapp.detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.part.sportpartapp.sportList.domain.repository.SportListRepository
import com.part.sportpartapp.sportList.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val sportListRepository: SportListRepository,
    saveInstanceHandle: SavedStateHandle
) :ViewModel() {
    private  val sportId=saveInstanceHandle.get<Int>("sportId")
    private  var _detailState= MutableStateFlow(DetailsState())
    val detailState =_detailState.asStateFlow()

    init {
        getSport(sportId ?:-1)
    }

    private fun getSport(id:Int){
         viewModelScope.launch {
             _detailState.update {
                 it.copy(isLoading = true)
             }
          sportListRepository.getSportById(id).collectLatest { result ->
              when(result){
                  is Resource.Error -> {
                      _detailState.update {
                          it.copy(isLoading = false)
                      }
                  }
                  is Resource.Loading -> {
                      _detailState.update {
                          it.copy(isLoading = result.isLoading)
                      }
                  }
                  is Resource.Success -> {

                      result.data?.let {sport->
                          _detailState.update {
                              it.copy(sport = sport)
                          }
                      }
                  }
              }
          }

         }
    }
}
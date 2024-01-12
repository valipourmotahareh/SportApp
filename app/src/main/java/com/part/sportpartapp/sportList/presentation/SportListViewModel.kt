package com.part.sportpartapp.sportList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.part.sportpartapp.sportList.domain.repository.SportListRepository
import com.part.sportpartapp.sportList.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportListViewModel @Inject constructor(
    private val sportListRepository: SportListRepository
) : ViewModel() {
    private var _sportListState = MutableStateFlow(SportListState())
    val sportListState = _sportListState.asStateFlow()

    init {
        showSportList()
    }

    fun onEvent(event: SportListUIEvent) {
        when (event) {
            SportListUIEvent.ShowAllListSport -> {
                showSportList()
            }
            is SportListUIEvent.Recommended -> {
                showSportListRecommended()
            }

            is SportListUIEvent.Free -> {
                showSportListFree()
            }

            is SportListUIEvent.SelectedType -> {
                filterSportListByType(event.type)
            }

        }
    }


    private fun showSportList() {
        viewModelScope.launch {
            _sportListState.update {
                it.copy(isLoading = true)
            }

            sportListRepository.getSportList().collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _sportListState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { sportList ->
                            _sportListState.update {
                                it.copy(
                                    SportList = sportList

                                )
                            }

                        }

                    }
                    is Resource.Loading -> {
                        _sportListState.update {
                            it.copy(isLoading = result.isLoading)
                        }

                    }
                }
            }
        }

    }

    private fun showSportListRecommended() {
        viewModelScope.launch {
            _sportListState.update {
                it.copy(isLoading = true)
            }

            sportListRepository.getSportListRecommended().collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _sportListState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { sportList ->
                            _sportListState.update {
                                it.copy(
                                    SportList = sportList,

                                    )
                            }

                        }

                    }
                    is Resource.Loading -> {
                        _sportListState.update {
                            it.copy(isLoading = result.isLoading)
                        }

                    }
                }
            }
        }

    }

    private fun showSportListFree() {
        viewModelScope.launch {
            _sportListState.update {
                it.copy(isLoading = true)
            }

            sportListRepository.getSportListFree().collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _sportListState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { sportList ->
                            _sportListState.update {
                                it.copy(
                                    SportList = sportList,

                                    )
                            }

                        }

                    }
                    is Resource.Loading -> {
                        _sportListState.update {
                            it.copy(isLoading = result.isLoading)
                        }

                    }
                }
            }
        }

    }

    private fun filterSportListByType(type: Int) {
        viewModelScope.launch {
            _sportListState.update {
                it.copy(isLoading = true)
            }

            sportListRepository.filterSportListByType(type).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _sportListState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { sportList ->
                            _sportListState.update {
                                it.copy(
                                    SportList = sportList,

                                    )
                            }

                        }

                    }
                    is Resource.Loading -> {
                        _sportListState.update {
                            it.copy(isLoading = result.isLoading)
                        }

                    }
                }
            }
        }

    }

}
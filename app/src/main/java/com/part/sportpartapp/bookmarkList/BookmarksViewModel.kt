package com.part.sportpartapp.bookmarkList

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
class BookmarksViewModel @Inject constructor(
    private val sportListRepository: SportListRepository,
) : ViewModel() {
    private  var _bookmarkState= MutableStateFlow(BookmarkState())
    val bookmarkState =_bookmarkState.asStateFlow()

    fun onEvent(event: BookmarkListUIEvent){
        when(event){
            is BookmarkListUIEvent.SetBookmark -> {
               setBookmark(event.id)
            }
            is BookmarkListUIEvent.ShowListBookmark ->{
                showBookmarkSportList()
            }
        }

    }

    private fun showBookmarkSportList(){
        viewModelScope.launch {
            _bookmarkState.update {
                it.copy(isLoading = true)
            }

            sportListRepository.getBookmarkSportList().collectLatest {
                    result -> when(result){
                is Resource.Error -> {
                    _bookmarkState.update {
                        it.copy( isLoading = false,
                         BookmarkSportList = emptyList())
                    }
                }
                is Resource.Success -> {
                    result.data?.let {bookmarkList ->
                        _bookmarkState.update{
                            it.copy(
                                BookmarkSportList=bookmarkList
                            )
                        }

                    }

                }
                is Resource.Loading -> {
                    _bookmarkState.update {
                        it.copy(isLoading = result.isLoading)
                    }

                }
            }
            }
        }

    }

    private fun setBookmark(id:Int){
        viewModelScope.launch {
            _bookmarkState.update {
                it.copy(isLoading = true)
            }

            sportListRepository.setBookmark(id).collectLatest {
                    result -> when(result){
                is Resource.Error -> {
                    _bookmarkState.update {
                        it.copy( isLoading = false)
                    }
                }
                is Resource.Success -> {
                    showBookmarkSportList()
                    result.data?.let { isBookmark: Boolean ->
                        _bookmarkState.update{
                            it.copy(
                                setBookmark=isBookmark,

                                )
                        }
                    }
                }
                is Resource.Loading -> {
                    _bookmarkState.update {
                        it.copy(isLoading = result.isLoading)
                    }

                }
            }
            }
        }

    }
}
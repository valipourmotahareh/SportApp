package com.part.sportpartapp.sportList.domain.repository

import com.part.sportpartapp.sportList.domain.model.Sport
import com.part.sportpartapp.sportList.util.Resource
import kotlinx.coroutines.flow.Flow


interface SportListRepository {

    suspend fun getSportList(
    ): Flow<Resource<List<Sport>>>

    suspend fun getSportById(
        id: Int
    ):
            Flow<Resource<Sport>>

    suspend fun getSportListRecommended():
            Flow<Resource<List<Sport>>>

    suspend fun getSportListFree():
            Flow<Resource<List<Sport>>>

    suspend fun filterSportListByType(
        type: Int
    ):
            Flow<Resource<List<Sport>>>

    suspend fun setBookmark(id: Int):
            Flow<Resource<Boolean>>

    suspend fun getBookmarkSportList():
            Flow<Resource<List<Sport>>>
}
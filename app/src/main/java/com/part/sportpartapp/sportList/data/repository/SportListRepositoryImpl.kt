package com.part.sportpartapp.sportList.data.repository

import com.part.sportpartapp.sportList.data.local.sport.SportDatabase
import com.part.sportpartapp.sportList.data.local.sport.SportEntity
import com.part.sportpartapp.sportList.data.mappers.toSport
import com.part.sportpartapp.sportList.data.mappers.toSportEntity
import com.part.sportpartapp.sportList.data.remote.SportApi
import com.part.sportpartapp.sportList.domain.model.Sport
import com.part.sportpartapp.sportList.domain.repository.SportListRepository
import com.part.sportpartapp.sportList.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class SportListRepositoryImpl @Inject constructor(
    private val sportApi: SportApi, private val sportDatabase: SportDatabase
) : SportListRepository {
    /*
        getSportList: first check local database: is list is empty get list from api
     */
    override suspend fun getSportList(): Flow<Resource<List<Sport>>> {
        return flow {
            emit(Resource.Loading(true))

            val localSportList = sportDatabase.sportDao.getSportList()

            if (localSportList.isNotEmpty()) {
                emit(Resource.Success(data = localSportList.map { sportEntity ->
                    sportEntity.toSport()
                }))

                emit(Resource.Loading(false))
                return@flow
            }

            val sportListFromApi = try {
                sportApi.getSportList()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }

            val sportEntities = sportListFromApi.let {
                it.map { sportListDto ->
                    sportListDto.toSportEntity()
                }
            }
            sportDatabase.sportDao.upsertSportList(sportEntities)
            emit(Resource.Success(sportEntities.map { it.toSport() }))

            emit(Resource.Loading(false))

        }
    }

    /*
       get sport by is for show detail sport
     */
    override suspend fun getSportById(id: Int): Flow<Resource<Sport>> {
        return flow {

            emit(Resource.Loading(true))

            val sportEntity = sportDatabase.sportDao.getSportById(id)

            emit(
                Resource.Success(sportEntity.toSport())
            )

            emit(Resource.Loading(false))
            return@flow
        }
    }
    /*
       get list of sports that are recommended
     */
    override suspend fun getSportListRecommended(): Flow<Resource<List<Sport>>> {
        return flow {

            emit(Resource.Loading(true))

            val sportListRecommended = sportDatabase.sportDao.getSportListRecommended()

            if (sportListRecommended.isNotEmpty()) {
                emit(Resource.Success(data = sportListRecommended.map { sportEntity ->
                    sportEntity.toSport()
                }))

                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error("Do Not  Exist Recommended Sport"))

            emit(Resource.Loading(false))


        }
    }

    /*
   get list that are free
  */
    override suspend fun getSportListFree(): Flow<Resource<List<Sport>>> {
        return flow {
            emit(Resource.Loading(true))

            val sportEntity = sportDatabase.sportDao.getSportListFree()

            if (sportEntity.isNotEmpty()) {
                emit(Resource.Success(data = sportEntity.map {
                    it.toSport()
                }))

                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error("Do Not  Exist Free Sport"))

            emit(Resource.Loading(false))
        }
    }

    /*
         filter sport list by type
     */
    override suspend fun filterSportListByType(type: Int): Flow<Resource<List<Sport>>> {
        return flow {
            emit(Resource.Loading(true))
            val sportEntity:List<SportEntity> = if (type==0) sportDatabase.sportDao.getSportList()
            else sportDatabase.sportDao.getSportListByType(type)

            if (sportEntity.isNotEmpty()) {
                emit(Resource.Success(data = sportEntity.map {
                    it.toSport()
                }))

                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error("Do not Exist Sport By This Type"))

            emit(Resource.Loading(false))
        }
    }

    /*
      set bookmark
     */
    override suspend fun setBookmark(id: Int): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))

            sportDatabase.sportDao.setBookmark(id)

            emit(Resource.Success(data = true))

            emit(Resource.Loading(false))
            return@flow
        }
    }

    /*
      get list that bookmarked
     */
    override suspend fun getBookmarkSportList(): Flow<Resource<List<Sport>>> {
        return flow {
            emit(Resource.Loading(true))

            val sportEntity = sportDatabase.sportDao.getSportListBookmark()

            if (sportEntity.isNotEmpty()) {
                emit(Resource.Success(data = sportEntity.map {
                    it.toSport()
                }))

                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error("Do Not Exist List Bookmarked"))

            emit(Resource.Loading(false))
        }
    }

}
package com.part.sportpartapp.sportList.data.remote

import com.part.sportpartapp.sportList.data.remote.respond.SportListDto
import retrofit2.http.GET

interface SportApi {
    companion object {
        const val BASE_URL = "https://mocki.io/"
    }

    @GET("v1/653f59ef-628b-4702-8d9e-19e5ba06e32e")
    suspend fun getSportList(): List<SportListDto>
}
package com.part.sportpartapp.di

import android.app.Application
import androidx.room.Room
import com.part.sportpartapp.sportList.data.local.sport.SportDatabase
import com.part.sportpartapp.sportList.data.remote.SportApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val interceptor:HttpLoggingInterceptor=HttpLoggingInterceptor().apply {
        level=HttpLoggingInterceptor.Level.BODY
    }

    private  val client:OkHttpClient=OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun providesSportApi(): SportApi {
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(SportApi.BASE_URL)
            .client(client)
            .build()
            .create(SportApi::class.java)
    }

    @Provides
    @Singleton
    fun providesSportDatabase(app:Application): SportDatabase {
        return Room.databaseBuilder(
            app,
            SportDatabase::class.java,
            "sportDb.db"
        ).build()
    }
}
package com.example.borutoapp.di

import android.content.Context
import androidx.room.Room
import com.example.borutoapp.data.local.BorutoDatabase
import com.example.borutoapp.data.local.repository.LocalDataSourceImpl
import com.example.borutoapp.domain.repository.LocalDataSource
import com.example.borutoapp.util.Constants.BORUTO_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): BorutoDatabase = Room.databaseBuilder(
        context = context,
        BorutoDatabase::class.java,
        BORUTO_DATABASE
    ).build()

    @Provides
    fun provideLocalDataSource(
        database: BorutoDatabase
    ): LocalDataSource = LocalDataSourceImpl(
        database
    )

}
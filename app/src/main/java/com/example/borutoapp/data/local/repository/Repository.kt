package com.example.borutoapp.data.local.repository

import androidx.paging.PagingData
import com.example.borutoapp.domain.model.Hero
import com.example.borutoapp.domain.repository.DataStoreOperations
import com.example.borutoapp.domain.repository.LocalDataSource
import com.example.borutoapp.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource,
    private val datastore: DataStoreOperations
) {

    fun getAllHeroes(): Flow<PagingData<Hero>> = remote.getAllHeroes()

    suspend fun saveOnboardingState(completed: Boolean) {
        datastore.saveOnboardingState(completed)
    }

    fun readOnboardingState(): Flow<Boolean> {
        return datastore.readOnboardingState()
    }

    fun searchHeroes(query: String): Flow<PagingData<Hero>> = remote.searchHeroes(query = query)

    suspend fun getSelectedHero(heroId: Int) = local
        .getSelectedHero(heroId)
}
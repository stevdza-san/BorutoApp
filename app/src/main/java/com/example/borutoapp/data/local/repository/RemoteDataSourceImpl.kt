package com.example.borutoapp.data.local.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.borutoapp.data.local.BorutoDatabase
import com.example.borutoapp.data.local.paging_source.HeroRemoteMediator
import com.example.borutoapp.data.local.paging_source.SearchHeroesSource
import com.example.borutoapp.data.remote.BorutoApi
import com.example.borutoapp.domain.model.Hero
import com.example.borutoapp.domain.repository.RemoteDataSource
import com.example.borutoapp.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class RemoteDataSourceImpl(
    private val borutoApi: BorutoApi,
    private val borutoDatabase: BorutoDatabase
) : RemoteDataSource {

    private val heroDao = borutoDatabase.heroDao()

    override fun getAllHeroes(): Flow<PagingData<Hero>> {

        val pagingSourceFactory = {heroDao.getAllHeroes()}

        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = HeroRemoteMediator(
                borutoApi,
                borutoDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow

    }
    override fun searchHeroes(query: String): Flow<PagingData<Hero>> = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE),
        pagingSourceFactory = {
            SearchHeroesSource(
                borutoApi = borutoApi,
                query = query
            )
        }
    ).flow
}
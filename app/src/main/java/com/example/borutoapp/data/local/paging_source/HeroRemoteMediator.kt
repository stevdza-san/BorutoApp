package com.example.borutoapp.data.local.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.borutoapp.data.local.BorutoDatabase
import com.example.borutoapp.data.remote.BorutoApi
import com.example.borutoapp.domain.model.Hero
import com.example.borutoapp.domain.model.HeroRemoteKeys
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class HeroRemoteMediator @Inject constructor(
    private val borutoApi: BorutoApi,
    private val borutoDatabase: BorutoDatabase
) : RemoteMediator<Int, Hero>() {

    private val heroDao = borutoDatabase.heroDao()
    private val heroRemoteKeysDao = borutoDatabase.heroRemoteKeysDao()
    private suspend fun getRemoteKeysClosestToCurrentPosition(
        state: PagingState<Int, Hero>
    ): HeroRemoteKeys? = state.anchorPosition?.let { position ->
        state.closestItemToPosition(position)?.id?.let { id ->
            heroRemoteKeysDao.getRemoteKeys(heroId = id)
        }
    }

    private suspend fun getRemoteKeysForFirstItem(
        state: PagingState<Int, Hero>
    ): HeroRemoteKeys? = state.pages.firstOrNull {
        it.data.isNotEmpty()
    } ?.data?.firstOrNull()?.let { hero ->
        heroRemoteKeysDao.getRemoteKeys(heroId = hero.id)
    }
    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = heroRemoteKeysDao.getRemoteKeys(heroId = 1)?.lastUpdated ?: 0L
        val cacheTimeout = 1400

        val differenceInMinuted = (currentTime - lastUpdated) / 1000 / 60
        return if (differenceInMinuted.toInt() <= cacheTimeout){
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, Hero>): HeroRemoteKeys? =
        state.pages.lastOrNull {
            it.data.isNotEmpty()
        } ?.data?.lastOrNull()?.let { hero ->
            heroRemoteKeysDao.getRemoteKeys(heroId = hero.id)
        }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Hero>): MediatorResult {
        try {
            val page: Int = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeysClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeysForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeysForLastItem(state)
                    val nextPage = remoteKeys ?. nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached =  remoteKeys != null
                    )
                    nextPage
                }
            }

            val response = borutoApi.getAllHeroes(page = page)
            if (response.heroes.isNotEmpty()) {
                borutoDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        heroDao.deleteAllHeroes()
                        heroRemoteKeysDao.deleteAllRemoteKeys()
                    }
                    val prevPage = response.prevPage
                    val nextPage = response.nextPage
                    val keys = response.heroes.map { hero ->
                        HeroRemoteKeys(
                            id = hero.id,
                            prevPage = prevPage,
                            nextPage = nextPage,
                            lastUpdated = response.lastUpdated
                        )
                    }
                    heroRemoteKeysDao.addAllRemoteKeys(keys)
                    heroDao.addHeroes(heroes = response.heroes)
                }
            }
            return MediatorResult.Success(endOfPaginationReached = response.nextPage  == null)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
    /*private fun parseMillis(millis: Long): String {
        val date = Date(millis)
        val format = SimpleDateFormat("yyyy.MM.dd hh:mm", Locale.ROOT)
        return format.format(date)
    }*/
}

package com.example.borutoapp.data.local.repository

import com.example.borutoapp.data.local.BorutoDatabase
import com.example.borutoapp.domain.model.Hero
import com.example.borutoapp.domain.repository.LocalDataSource

class LocalDataSourceImpl(
    borutoDataSource: BorutoDatabase
) : LocalDataSource {
    private val heroDao = borutoDataSource.heroDao()
    override suspend fun getSelectedHero(heroId: Int): Hero = heroDao.getSelectedHero(heroId)

}
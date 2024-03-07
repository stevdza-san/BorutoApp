package com.example.borutoapp.domain.use_cases.search_heroes

import androidx.paging.PagingData
import com.example.borutoapp.data.local.repository.Repository
import com.example.borutoapp.domain.model.Hero
import kotlinx.coroutines.flow.Flow

class SearchHeroesUseCase(
    private val repository: Repository
) {
    operator fun invoke(query: String): Flow<PagingData<Hero>> = repository.searchHeroes(query)
}
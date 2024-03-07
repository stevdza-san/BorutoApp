package com.example.borutoapp.di

import android.content.Context
import com.example.borutoapp.data.local.repository.DataStoreOperationsImp
import com.example.borutoapp.data.local.repository.Repository
import com.example.borutoapp.domain.repository.DataStoreOperations
import com.example.borutoapp.domain.use_cases.Usecases
import com.example.borutoapp.domain.use_cases.get_all_heroes.GetAllHeroesUseCase
import com.example.borutoapp.domain.use_cases.get_selected_hero.GetSelectedHeroUseCase
import com.example.borutoapp.domain.use_cases.read_onboarding.ReadOnboardingUseCase
import com.example.borutoapp.domain.use_cases.save_onboarding.SaveOnboardingUseCase
import com.example.borutoapp.domain.use_cases.search_heroes.SearchHeroesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideDataStoreOperations(
        @ApplicationContext context: Context
    ): DataStoreOperations {
        return DataStoreOperationsImp(context = context)
    }

    @Provides
    fun provideUseCases(repository: Repository): Usecases {
        return Usecases(
            saveOnboardingUseCase = SaveOnboardingUseCase(repository),
            readOnboardingUseCase = ReadOnboardingUseCase(repository),
            getAllHeroesUseCase = GetAllHeroesUseCase(repository),
            searchHeroesUseCase = SearchHeroesUseCase(repository),
            getSelectedHeroUseCase = GetSelectedHeroUseCase(repository)
        )
    }
}
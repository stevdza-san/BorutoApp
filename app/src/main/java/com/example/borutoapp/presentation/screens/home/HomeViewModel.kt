package com.example.borutoapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.example.borutoapp.domain.use_cases.Usecases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    usecases: Usecases
) : ViewModel() {
    val getAllHeroes = usecases.getAllHeroesUseCase()
}
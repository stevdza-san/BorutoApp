package com.example.borutoapp.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.borutoapp.domain.use_cases.Usecases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val usecases: Usecases
) : ViewModel() {
    private val _onboardingCompleted = MutableStateFlow(false)
    val onboardingStateFlow: StateFlow<Boolean> = _onboardingCompleted

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _onboardingCompleted.value = usecases.readOnboardingUseCase().stateIn(viewModelScope).value
        }
    }
}
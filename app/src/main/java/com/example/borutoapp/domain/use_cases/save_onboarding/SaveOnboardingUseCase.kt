package com.example.borutoapp.domain.use_cases.save_onboarding

import com.example.borutoapp.data.local.repository.Repository
import javax.inject.Inject

class SaveOnboardingUseCase constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(completed: Boolean) {
        repository.saveOnboardingState(completed)
    }
}
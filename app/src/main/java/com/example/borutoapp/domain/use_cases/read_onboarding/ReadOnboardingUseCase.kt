package com.example.borutoapp.domain.use_cases.read_onboarding

import com.example.borutoapp.data.local.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadOnboardingUseCase constructor(
    val repository: Repository
) {
    operator fun invoke(): Flow<Boolean> {
        return repository.readOnboardingState()
    }
}
package com.example.borutoapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class APIResponse(
    val heroes: List<Hero> = emptyList(),
    val lastUpdated: Long? = null,
    val message: String? = null,
    val nextPage: Int? = null,
    val prevPage: Int? = null,
    val success: Boolean? = null
)
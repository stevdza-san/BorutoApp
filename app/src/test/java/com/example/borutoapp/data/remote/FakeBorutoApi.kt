package com.example.borutoapp.data.remote

import com.example.borutoapp.domain.model.APIResponse
import com.example.borutoapp.domain.model.Hero

class FakeBorutoApi : BorutoApi {

    private val heroes = listOf(
        Hero(
            heroId = 1,
            heroName = "Sasuke",
            heroImage = "",
            heroAbout = "",
            heroRating = 5.0,
            heroPower = 0,
            heroMonth = "",
            heroDay = "",
            heroFamily = listOf(),
            heroAbilities = listOf(),
            heroNatureTypes = listOf()
        ),
        Hero(
            heroId = 2,
            heroName = "Naruto",
            heroImage = "",
            heroAbout = "",
            heroRating = 5.0,
            heroPower = 0,
            heroMonth = "",
            heroDay = "",
            heroFamily = listOf(),
            heroAbilities = listOf(),
            heroNatureTypes = listOf()
        ),
        Hero(
            heroId = 3,
            heroName = "Sakura",
            heroImage = "",
            heroAbout = "",
            heroRating = 5.0,
            heroPower = 0,
            heroMonth = "",
            heroDay = "",
            heroFamily = listOf(),
            heroAbilities = listOf(),
            heroNatureTypes = listOf()
        )
    )

    override suspend fun getAllHeroes(page: Int): APIResponse {
        return APIResponse(
            success = false
        )
    }

    override suspend fun searchHeroes(name: String): APIResponse {
        val searchedHeroes = findHeroes(name = name)
        return APIResponse(
            success = true,
            message = "ok",
            heroes = searchedHeroes
        )
    }

    private fun findHeroes(name: String): List<Hero> {
        val founded = mutableListOf<Hero>()
        return if (name.isNotEmpty()) {
            heroes.forEach { hero ->
                if (hero.heroName.lowercase().contains(name.lowercase())) {
                    founded.add(hero)
                }
            }
            founded
        } else {
            emptyList()
        }
    }
}
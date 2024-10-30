package com.example.borutoapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.borutoapp.util.Constants.HERO_DATABASE_TABLE
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = HERO_DATABASE_TABLE)
data class Hero(
    @PrimaryKey(autoGenerate = false)
    val heroId: Int,
    val heroAbilities: List<String>,
    val heroAbout: String,
    val heroDay: String,
    val heroFamily: List<String>,
    val heroImage: String,
    val heroMonth: String,
    val heroName: String,
    val heroNatureTypes: List<String>,
    val heroPower: Int,
    val heroRating: Double
)
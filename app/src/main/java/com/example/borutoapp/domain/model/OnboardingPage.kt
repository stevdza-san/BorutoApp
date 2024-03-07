package com.example.borutoapp.domain.model

import androidx.annotation.DrawableRes
import com.example.borutoapp.R
import dagger.multibindings.IntoMap

sealed class OnboardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First: OnboardingPage(
        image = R.drawable.greetings,
        title = "Greeting",
        description = "Are you a Boruto fan? Because if you are, then we have great news for you"
    )
    object Second: OnboardingPage(
        image = R.drawable.explore,
        title = "Explore",
        description = "Find your favourite and learn some of the things that you didn't know about!"
    )
    object Third: OnboardingPage(
        image = R.drawable.power,
        title = "Power",
        description = "Check out your hero's power and see how much are they strong compared to others."
    )
}


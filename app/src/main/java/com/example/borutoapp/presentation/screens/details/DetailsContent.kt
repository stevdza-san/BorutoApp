package com.example.borutoapp.presentation.screens.details

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.borutoapp.R
import com.example.borutoapp.domain.model.Hero
import com.example.borutoapp.presentation.common.InfoBox
import com.example.borutoapp.presentation.components.OrderedList
import com.example.borutoapp.ui.theme.INFO_ICON_SIZE
import com.example.borutoapp.ui.theme.LARGE_PADDING
import com.example.borutoapp.ui.theme.MEDIUM_PADDING
import com.example.borutoapp.ui.theme.MIN_SHEET_HEIGHT
import com.example.borutoapp.ui.theme.SMALL_PADDING
import com.example.borutoapp.ui.theme.titleColor
import com.example.borutoapp.util.Constants.ABOUT_TEXT_MAX_LINES
import com.example.borutoapp.util.Constants.BASE_URL
import kotlin.math.absoluteValue
import kotlin.math.sign
import kotlin.math.ulp
import kotlin.math.withSign

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailsContent(
    navController: NavHostController,
    selectedHero: Hero?
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )
    val currentSheetFraction = scaffoldState.currentSheetFraction

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = MIN_SHEET_HEIGHT,
        sheetContent = {
            selectedHero?.let { hero ->
                BottomSheetContent(selectedHero = hero)
            }
        },
        content = {
            selectedHero?.let { hero ->
                BackgroundContent(
                    imageFraction = currentSheetFraction,
                    heroImage = hero.image,
                    onClosedClicked = navController::popBackStack
                )
            }
        }

    )

}

@Composable
fun BottomSheetContent(
    selectedHero: Hero,
    infoBoxIconColor: Color = MaterialTheme.colors.primary,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.titleColor
) {
    Column(
        modifier = Modifier
            .background(sheetBackgroundColor)
            .padding(LARGE_PADDING)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = LARGE_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(INFO_ICON_SIZE)
                    .weight(2f),
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = stringResource(id = R.string.app_logo),
                tint = contentColor
            )
            Text(
                modifier = Modifier
                    .weight(8f),
                text = selectedHero.name,
                color = contentColor,
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoBox(icon = painterResource(id = R.drawable.ic_bolt),
                iconColor = infoBoxIconColor,
                bigText = "${selectedHero.power}",
                smallText = stringResource(R.string.power),
                textColor =contentColor
            )
            InfoBox(icon = painterResource(id = R.drawable.ic_calender),
                iconColor = infoBoxIconColor,
                bigText = selectedHero.month,
                smallText = stringResource(R.string.month),
                textColor =contentColor
            )
            InfoBox(icon = painterResource(id = R.drawable.ic_cake),
            iconColor = infoBoxIconColor,
            bigText = selectedHero.day,
            smallText = stringResource(R.string.birthday),
            textColor =contentColor
            )
        }
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.about),
            color = contentColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .padding(bottom = MEDIUM_PADDING)
                .fillMaxWidth(),
            text = selectedHero.about,
            color = contentColor,
            fontSize = MaterialTheme.typography.body1.fontSize,
            maxLines = ABOUT_TEXT_MAX_LINES,
            textAlign = TextAlign.Justify
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OrderedList(
                title = stringResource(R.string.family),
                items = selectedHero.family,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(R.string.abilities),
                items = selectedHero.abilities,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(R.string.nature_types),
                items = selectedHero.natureTypes,
                textColor = contentColor
            )
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun BackgroundContent(
    heroImage: String,
    imageFraction: Float = 1f,
    backgroundColor: Color = MaterialTheme.colors.background,
    onClosedClicked: () -> Unit
) {
    val imageUrl = "$BASE_URL${heroImage}"
    val painter = rememberImagePainter(imageUrl) {
        error(R.drawable.ic_placeholder)
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(backgroundColor)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(imageFraction)
                .align(Alignment.TopStart),
            painter = painter, 
            contentDescription = stringResource(id = R.string.hero_image),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                modifier = Modifier
                    .padding(all = SMALL_PADDING),
                onClick = onClosedClicked
            ) {
                Icon(
                    modifier = Modifier
                        .size(INFO_ICON_SIZE),
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close),
                    tint = Color.White
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
val BottomSheetScaffoldState.currentSheetFraction: Float
    get() {
        Log.d("DetailsContent", "Current value = ${bottomSheetState.progress}")
        Log.d("DetailsContent", "Collapsed = ${1f - bottomSheetState.progress}")
        Log.d("DetailsContent", "Expanded = ${0f + bottomSheetState.progress}")
        Log.d("DetailsContent", "Direction = ${0f + bottomSheetState.requireOffset()}")


        return when {
            bottomSheetState.progress < 1f && bottomSheetState.isCollapsed -> 1f - bottomSheetState.progress
            bottomSheetState.progress < 1f  && bottomSheetState.isExpanded -> 0f + bottomSheetState.progress
            bottomSheetState.isCollapsed -> 1f
            bottomSheetState.isExpanded  -> 0f
            else -> bottomSheetState.progress
        }
    }

@Composable
@Preview
fun BottomSheetContentPreview() = BottomSheetContent(selectedHero = Hero(
    id = 11,
    name = "Momoshiki",
    image = "/images/momoshiki.jpg",
    about = "Momoshiki Ōtsutsuki (大筒木モモシキ, Ōtsutsuki Momoshiki) was a member of the Ōtsutsuki clan's main family, sent to investigate the whereabouts of Kaguya and her God Tree and then attempting to cultivate a new one out of the chakra of the Seventh Hokage. In the process of being killed by Boruto Uzumaki, Momoshiki placed a Kāma on him, allowing his spirit to remain intact through the mark.",
    rating = 3.9,
    power = 98,
    month = "Jan",
    day = "1st",
    family = listOf(
        "Otsutsuki Clan"
    ),
    abilities = listOf(
        "Rinnegan",
        "Byakugan",
        "Strength"
    ),
    natureTypes = listOf(
        "Fire",
        "Lightning",
        "Wind",
        "Water",
        "Earth"
    )
))

@Composable
@Preview
fun DetailsContentPreview() = DetailsContent(navController = rememberNavController(), selectedHero = Hero(
    id = 11,
    name = "Momoshiki",
    image = "/images/momoshiki.jpg",
    about = "Momoshiki Ōtsutsuki (大筒木モモシキ, Ōtsutsuki Momoshiki) was a member of the Ōtsutsuki clan's main family, sent to investigate the whereabouts of Kaguya and her God Tree and then attempting to cultivate a new one out of the chakra of the Seventh Hokage. In the process of being killed by Boruto Uzumaki, Momoshiki placed a Kāma on him, allowing his spirit to remain intact through the mark.",
    rating = 3.9,
    power = 98,
    month = "Jan",
    day = "1st",
    family = listOf(
        "Otsutsuki Clan"
    ),
    abilities = listOf(
        "Rinnegan",
        "Byakugan",
        "Strength"
    ),
    natureTypes = listOf(
        "Fire",
        "Lightning",
        "Wind",
        "Water",
        "Earth"
    )
))
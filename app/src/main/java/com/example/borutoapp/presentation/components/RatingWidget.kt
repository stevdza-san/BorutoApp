package com.example.borutoapp.presentation.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.borutoapp.R
import com.example.borutoapp.ui.theme.EXTRA_SMALL_PADDING
import com.example.borutoapp.ui.theme.LightGray
import com.example.borutoapp.ui.theme.StarColor

@Composable
fun RatingWidget(
    modifier: Modifier,
    rating: Double,
    scaleFactor: Float = 3f,
    spaceBetween: Dp = EXTRA_SMALL_PADDING
) {
    val result = calculateStars(rating = rating)
    val starStringPath = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starStringPath).toPath()
    }

    val starPathBounds = remember {
        starPath.getBounds()
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        result["filledStars"]?.let {
            repeat(it) {
                FilledStar(starPath = starPath, starPathBound = starPathBounds, scaleFactor = scaleFactor)
            }
        }
        result["halfFilledStars"]?.let {
            repeat(it) {
                HalfFilledStar(starPath = starPath, starPathBound = starPathBounds, scaleFactor = scaleFactor)
            }
        }
        result["emptyStars"]?.let {
            repeat(it) {
                EmptyStar(starPath = starPath, starPathBound = starPathBounds, scaleFactor = scaleFactor)
            }
        }
    }
}

@Composable
fun FilledStar(
    starPath: Path,
    starPathBound: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        scale(scale = scaleFactor) {
            val canvasCenter = center
            val pathWayCenter = starPathBound.center
            val left = canvasCenter.x - pathWayCenter.x
            val top = canvasCenter.y - pathWayCenter.y
            translate(
                left = left,
                top = top
            ) {
                drawPath(
                    path = starPath,
                    color = StarColor
                )
            }
        }
    }
}

@Composable
fun HalfFilledStar(
    starPath: Path,
    starPathBound: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        scale(scale = scaleFactor) {
            val canvasCenter = this.center
            val pathWayCenter = starPathBound.center
            val left = canvasCenter.x - pathWayCenter.x
            val top = canvasCenter.y - pathWayCenter.y
            translate(
                left = left,
                top = top
            ) {
                drawPath(
                    path = starPath,
                    color = LightGray.copy(alpha = 0.5f)
                )
                clipPath(path = starPath) {
                    drawRect(
                        color = StarColor,
                        size = Size(
                            width = starPathBound.center.x,
                            height = starPathBound.maxDimension * scaleFactor
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyStar(
    starPath: Path,
    starPathBound: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        scale(scale = scaleFactor) {
            val canvasCenter = this.center
            val pathWayCenter = starPathBound.center
            val left = canvasCenter.x - pathWayCenter.x
            val top = canvasCenter.y - pathWayCenter.y
            translate(
                left = left,
                top = top
            ) {
                drawPath(
                    path = starPath,
                    color = LightGray.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Composable
fun calculateStars(rating: Double): Map<String, Int> {
    val maxStars by remember { mutableStateOf(5) }
    var filledStars by remember { mutableStateOf(0) }
    var halfFilledStars by remember { mutableStateOf(0) }
    var emptyStars by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = rating){
        val (firstNum, lastNum) = rating.toString()
            .split(".")
            .map(String::toInt)
        if (firstNum in 0 .. 5 && lastNum in 0 .. 9) {
            filledStars = firstNum
            if (lastNum in 1 .. 5) {
                halfFilledStars++
            }
            if (lastNum in 6 .. 9) {
                filledStars++
            }
            if (firstNum >= 5 && lastNum > 0) {
                filledStars = 0
                halfFilledStars = 0
                emptyStars = 5
            }
        } else {
            Log.d("RatingWidget", "Invalid rating number")
        }

    }
    emptyStars = maxStars - (filledStars + halfFilledStars)
    return mapOf(
        "filledStars" to filledStars,
        "halfFilledStars" to halfFilledStars,
        "emptyStars" to emptyStars
    )
}

@Composable
@Preview(showBackground = true)
fun FilledStarPreview() {
    val starStringPath = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starStringPath).toPath()
    }

    val starPathBounds = remember {
        starPath.getBounds()
    }
    FilledStar(starPath = starPath, starPathBound = starPathBounds, scaleFactor = 3f)
}


@Composable
@Preview(showBackground = true)
fun HalfFilledStarPreview() {
    val starStringPath = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starStringPath).toPath()
    }

    val starPathBounds = remember {
        starPath.getBounds()
    }
    HalfFilledStar(starPath = starPath, starPathBound = starPathBounds, scaleFactor = 3f)
}


@Composable
@Preview(showBackground = true)
fun EmptydStarPreview() {
    val starStringPath = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starStringPath).toPath()
    }

    val starPathBounds = remember {
        starPath.getBounds()
    }
    EmptyStar(starPath = starPath, starPathBound = starPathBounds, scaleFactor = 3f)
}
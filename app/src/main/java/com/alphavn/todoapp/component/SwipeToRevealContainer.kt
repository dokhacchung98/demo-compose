package com.alphavn.todoapp.component

import android.annotation.SuppressLint
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

private const val MIN_DRAG = 5

/**
 * Creates revealable container with a swipe
 *
 * @param modifier to outer most composable
 * @param swipeToRevealParameters - parameters to tweak the animation
 * @param isRevealed - if the item is already swiped open
 * @param onExpand - callback for when the item is expanded
 * @param onCollapse - callback for when the item is collapsed
 * @param rowContent - content to display in the row
 * @param actionContent - content to display underneath the row to be revealed
 */
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun SwipeToRevealContainer(
    modifier: Modifier = Modifier,
    swipeToRevealParameters: SwipeToRevealParameters,
    isRevealed: Boolean,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
    rowContent: @Composable () -> Unit,
    actionContent: @Composable (RowScope.() -> Unit)
) {

    val transitionState = remember {
        MutableTransitionState(isRevealed).apply {
            targetState = !isRevealed
        }
    }
    val transition = updateTransition(transitionState, "cardTransition")

    val offsetTransition by transition.animateFloat(
        label = "offsetTransition",
        transitionSpec = { tween(durationMillis = swipeToRevealParameters.swipeToRevealAnimationDurationMs) },
        targetValueByState = { if (isRevealed) swipeToRevealParameters.cardOffset.value else 0f },
    )

    val alphaTransition by transition.animateFloat(
        label = "alphaTransition",
        transitionSpec = { tween(durationMillis = swipeToRevealParameters.swipeToRevealAnimationDurationMs) },
        targetValueByState = { if (isRevealed) 1f else 0f },
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        ActionRow(
            alpha = alphaTransition,
            actionContent = actionContent,
        )
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
                .offset { IntOffset(offsetTransition.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        when {
                            dragAmount >= MIN_DRAG -> onExpand()
                            dragAmount < -MIN_DRAG -> onCollapse()
                        }
                    }
                },
        ) {
            rowContent()
        }
    }
}

@Composable
private fun ActionRow(
    alpha: Float,
    actionContent: @Composable (RowScope.() -> Unit)
) {
    Row(
        Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .alpha(alpha = alpha)
    ) {
        actionContent(this)
    }
}
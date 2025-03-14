package com.harsimran.animations.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentScreen() {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE8EAF6), // Light Indigo
                        Color(0xFFC5CAE9)  // Light Blue
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedContent(
                targetState = expanded,
                transitionSpec = {
                    // Define the transition spec here
                    if (targetState) {
                        // Expanding
                        slideInVertically(
                            animationSpec = tween(600, easing = FastOutSlowInEasing)
                        ) { -it } + fadeIn(
                            animationSpec = tween(600, easing = FastOutSlowInEasing)
                        ) with slideOutVertically(
                            animationSpec = tween(400, easing = FastOutSlowInEasing)
                        ) { it } + fadeOut(
                            animationSpec = tween(400, easing = FastOutSlowInEasing)
                        )
                    } else {
                        // Collapsing
                        slideInVertically(
                            animationSpec = tween(600, easing = FastOutSlowInEasing)
                        ) { it } + fadeIn(
                            animationSpec = tween(600, easing = FastOutSlowInEasing)
                        ) with slideOutVertically(
                            animationSpec = tween(400, easing = FastOutSlowInEasing)
                        ) { -it } + fadeOut(
                            animationSpec = tween(400, easing = FastOutSlowInEasing)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
            ) { targetExpanded ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(if (targetExpanded) 240.dp else 120.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = if (targetExpanded) {
                                    listOf(Color(0xFF2196F3), Color(0xFF1976D2)) // Blue gradient
                                } else {
                                    listOf(Color(0xFFF44336), Color(0xFFD32F2F)) // Red gradient
                                }
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Crossfade(
                        targetState = targetExpanded,
                        animationSpec = tween(400)
                    ) { isExpanded ->
                        Text(
                            text = if (isExpanded) "Expanded Content\nMore Details Here" else "Collapsed",
                            color = Color.White,
                            fontSize = if (isExpanded) 20.sp else 16.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            AnimatedButton(
                isExpanded = expanded,
                onClick = { expanded = !expanded }
            )
        }
    }
}

@Composable
fun AnimatedButton(
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    val buttonScale by animateFloatAsState(
        targetValue = if (isExpanded) 1.0f else 1.1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    Button(
        onClick = onClick,
        modifier = Modifier
            .scale(buttonScale)
            .width(200.dp)
            .height(56.dp)
            .shadow(4.dp, RoundedCornerShape(28.dp)),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF2196F3)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Crossfade(targetState = isExpanded) { expanded ->
            Text(
                text = if (expanded) "Collapse" else "Expand",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

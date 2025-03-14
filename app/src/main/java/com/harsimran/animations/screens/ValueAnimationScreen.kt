package com.harsimran.animations.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
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

@Composable
fun ValueAnimationScreen() {
    var isExpanded by remember { mutableStateOf(false) }

    // Animation states with custom specs
    val cardHeight by animateDpAsState(
        targetValue = if (isExpanded) 300.dp else 150.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    val cornerRadius by animateDpAsState(
        targetValue = if (isExpanded) 24.dp else 16.dp,
        animationSpec = tween(600, easing = FastOutSlowInEasing)
    )

    val offsetY by animateDpAsState(
        targetValue = if (isExpanded) 0.dp else 20.dp,
        animationSpec = tween(700, easing = FastOutSlowInEasing)
    )

    val backgroundAlpha by animateFloatAsState(
        targetValue = if (isExpanded) 1f else 0.7f,
        animationSpec = tween(500)
    )

    val gradientColors = listOf(
        Color(0xFF6200EE), // Purple
        Color(0xFF03DAC5)  // Teal
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Light gray background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Animated Profile Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight)
                    .offset(y = offsetY)
                    .shadow(12.dp, RoundedCornerShape(cornerRadius))
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = gradientColors,
                            startY = 0f,
                            endY = cardHeight.value
                        )
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = backgroundAlpha))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Profile Info
                    Text(
                        text = "Profile Card",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    AnimatedVisibility(
                        visible = isExpanded,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Age: 25\nLocation: New York\nStatus: Active",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 16.sp,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedButton(
                                onClick = { /* Action */ },
                                border = BorderStroke(1.dp, Color.White),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = Color.White
                                )
                            ) {
                                Text("View More")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Control Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AnimatedControlButton(
                    text = if (isExpanded) "Collapse" else "Expand",
                    onClick = { isExpanded = !isExpanded },
                    isPrimary = true
                )

                AnimatedControlButton(
                    text = "Reset",
                    onClick = { isExpanded = false },
                    isPrimary = false
                )
            }
        }
    }
}

@Composable
fun AnimatedControlButton(
    text: String,
    onClick: () -> Unit,
    isPrimary: Boolean
) {
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy
        )
    )

    Button(
        onClick = onClick,
        modifier = Modifier
            .scale(scale)
            .height(48.dp)
            .width(120.dp)
            .shadow(4.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPrimary) Color(0xFF6200EE) else Color(0xFF757575),
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

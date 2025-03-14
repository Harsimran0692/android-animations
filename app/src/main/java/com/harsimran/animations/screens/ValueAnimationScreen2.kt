package com.harsimran.animations.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ValueAnimationScreen2() {
    var isActive by remember { mutableStateOf(false) }

    // Animate individual colors
    val startColor by animateColorAsState(
        targetValue = if (isActive) Color(0xFF00E676) else Color(0xFF3F51B5),
        animationSpec = tween(1000)
    )
    val midColor by animateColorAsState(
        targetValue = if (isActive) Color(0xFF00C853) else Color(0xFF2196F3),
        animationSpec = tween(1000)
    )
    val endColor by animateColorAsState(
        targetValue = if (isActive) Color(0xFF00E676) else Color(0xFF3F51B5),
        animationSpec = tween(1000)
    )

    // Construct the gradient list from animated colors
    val gradientColors = listOf(startColor, midColor, endColor)

    val rotation by animateFloatAsState(
        targetValue = if (isActive) 360f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val size by animateDpAsState(
        targetValue = if (isActive) 160.dp else 120.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    val glowRadius by animateDpAsState(
        targetValue = if (isActive) 24.dp else 12.dp,
        animationSpec = tween(600, easing = FastOutSlowInEasing)
    )

    val pulse by animateFloatAsState(
        targetValue = if (isActive) 1.05f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF121212),
                        Color(0xFF1E1E1E)
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
            Box(
                modifier = Modifier
                    .size(size)
                    .scale(pulse)
                    .shadow(glowRadius, CircleShape, ambientColor = midColor.copy(alpha = 0.5f))
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = gradientColors,
                            radius = size.value * 0.8f
                        )
                    )
                    .border(2.dp, Color.White.copy(alpha = 0.7f), CircleShape)
                    .rotate(if (isActive) rotation else 0f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Power",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    AnimatedVisibility(
                        visible = isActive,
                        enter = fadeIn() + scaleIn(animationSpec = tween(400)),
                        exit = fadeOut() + scaleOut(animationSpec = tween(400))
                    ) {
                        Text(
                            text = "Activated",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF212121).copy(alpha = 0.95f)
                ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        AnimatedControlButton(
                            text = if (isActive) "Deactivate" else "Activate",
                            onClick = { isActive = !isActive },
                            isPrimary = true
                        )
                        AnimatedControlButton(
                            text = "Reset",
                            onClick = { isActive = false },
                            isPrimary = false
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StatusIndicator("Size", "${size.value.toInt()}dp", isActive)
                        StatusIndicator("State", if (isActive) "ON" else "OFF", isActive)
                    }
                }
            }
        }
    }
}


@Composable
fun AnimatedControlButton2(
    text: String,
    onClick: () -> Unit,
    isPrimary: Boolean
) {
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy
        )
    )

    Button(
        onClick = onClick,
        modifier = Modifier
            .scale(scale)
            .height(52.dp)
            .width(140.dp)
            .shadow(6.dp, RoundedCornerShape(26.dp)),
        shape = RoundedCornerShape(26.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPrimary) Color(0xFF2196F3) else Color(0xFF424242),
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 12.dp
        )
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun StatusIndicator(
    label: String,
    value: String,
    isActive: Boolean
) {
    val textColor by animateColorAsState(
        targetValue = if (isActive) Color(0xFF00E676) else Color(0xFFB0BEC5),
        animationSpec = tween(400)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 14.sp
        )
        Text(
            text = value,
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

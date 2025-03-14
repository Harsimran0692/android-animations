package com.harsimran.animations.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs

@Composable
fun GestureAnimationScreen() {
    // State for position and additional animations
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }

    // Animated properties
    val animatedOffsetX by animateDpAsState(
        targetValue = offsetX.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )
    val animatedOffsetY by animateDpAsState(
        targetValue = offsetY.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )
    val scale by animateFloatAsState(
        targetValue = if (isDragging) 1.1f else 1f,
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    )
    val rotation by animateFloatAsState(
        targetValue = if (isDragging) 10f else 0f,
        animationSpec = tween(400)
    )
    val glowRadius by animateDpAsState(
        targetValue = if (isDragging) 16.dp else 8.dp,
        animationSpec = tween(300)
    )
    val orbSize = 120.dp
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val maxWidth = with(density) { (configuration.screenWidthDp.dp - orbSize).toPx() }
    val maxHeight = with(density) { (configuration.screenHeightDp.dp - orbSize).toPx() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFECEFF1), // Light gray
                        Color(0xFFCFD8DC)  // Light blue-gray
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
            // Interactive Orb
            Box(
                modifier = Modifier
                    .offset(animatedOffsetX, animatedOffsetY)
                    .size(120.dp)
                    .scale(scale)
                    .rotate(rotation)
                    .shadow(glowRadius, CircleShape, ambientColor = Color(0xFF00BCD4).copy(alpha = 0.5f))
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF00BCD4), // Cyan
                                Color(0xFF0097A7)  // Darker cyan
                            )
                        )
                    )
                    .border(2.dp, Color.White.copy(alpha = 0.7f), CircleShape)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { _ -> isDragging = true },
                            onDragEnd = { isDragging = false },
                            onDragCancel = { isDragging = false },
                            onDrag = { change, dragAmount ->
                                offsetX += dragAmount.x
                                offsetY += dragAmount.y
                                // Boundary constraints using screen dimensions
                                offsetX = offsetX.coerceIn(-maxWidth / 2, maxWidth / 2)
                                offsetY = offsetY.coerceIn(-maxHeight / 2, maxHeight / 2)
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Drag Me",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Control and Status Panel
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.95f)
                ),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Gesture Controls",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF212121)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatusIndicator2("X", "${animatedOffsetX.value.toInt()}dp", isDragging)
                        StatusIndicator2("Y", "${animatedOffsetY.value.toInt()}dp", isDragging)
                        StatusIndicator2("State", if (isDragging) "Dragging" else "Idle", isDragging)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { offsetX = 0f; offsetY = 0f },
                        modifier = Modifier
                            .height(48.dp)
                            .width(120.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00BCD4),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Reset",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatusIndicator2(
    label: String,
    value: String,
    isActive: Boolean
) {
    val textColor by animateColorAsState(
        targetValue = if (isActive) Color(0xFF00BCD4) else Color(0xFF757575),
        animationSpec = tween(300)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            color = Color(0xFF424242),
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

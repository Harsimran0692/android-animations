package com.harsimran.animations.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController) {
    var visible by remember { mutableStateOf(true) }

    // Gradient background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF3F51B5), // Indigo
                        Color(0xFF2196F3)  // Blue
                    )
                )
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Student Info Card with animation
            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(8.dp, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.95f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Student Info",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color(0xFF3F51B5),
                            fontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Name: Harsimran Singh\nID: 301500536\nCourse: MAPD-721 Advance Android Development",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = Color.DarkGray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Enhanced Buttons with animation
            listOf(
                "animatedContent" to "Animated Content Demo",
                "valueAnimation" to "Value Animation Demo",
                "valueAnimation2" to "Value Animation 2 Demo",
                "gestureAnimation" to "Gesture Animation Demo"
            ).forEachIndexed { index, (route, title) ->
                AnimatedButton(
                    text = title,
                    onClick = { navController.navigate(route) },
                    delay = index * 100
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Toggle button
            Button(
                onClick = { visible = !visible },
                modifier = Modifier
                    .width(200.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF3F51B5)
                )
            ) {
                Text(
                    text = if (visible) "Hide Info" else "Show Info",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun AnimatedButton(
    text: String,
    onClick: () -> Unit,
    delay: Int
) {
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(delay.toLong())
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(
            initialOffsetX = { -it }
        ) + fadeIn()
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .shadow(4.dp, RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White.copy(alpha = 0.9f),
                contentColor = Color(0xFF2196F3)
            )
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
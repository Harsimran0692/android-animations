package com.harsimran.animations.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.harsimran.animations.screens.AnimatedContentScreen
import com.harsimran.animations.screens.GestureAnimationScreen
import com.harsimran.animations.screens.MainScreen
import com.harsimran.animations.screens.ValueAnimationScreen
import com.harsimran.animations.screens.ValueAnimationScreen2

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("animatedContent") { AnimatedContentScreen() }
        composable("valueAnimation") { ValueAnimationScreen() }
        composable("valueAnimation2") { ValueAnimationScreen2() }
        composable("gestureAnimation") { GestureAnimationScreen() }
    }
}
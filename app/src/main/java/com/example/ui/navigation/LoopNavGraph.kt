package com.example.ui.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.data.LoopRepository
import com.example.ui.screens.AppOnboardingScreen
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.SplashScreen

/**
 * Type-safe / sealed navigation routes for the Loop app navigation graph.
 * Manages the flow: Splash -> Onboarding -> Auth -> MainApp.
 */
sealed class LoopRoute(val route: String) {
    object Splash : LoopRoute("splash")
    object Onboarding : LoopRoute("onboarding")
    object Auth : LoopRoute("auth")
    object MainApp : LoopRoute("main_app")
}

@Composable
fun LoopNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = LoopRoute.Splash.route,
    onLoginSuccess: () -> Unit = {},
    mainAppContent: @Composable () -> Unit
) {
    val isAuthenticated by LoopRepository.isAuthenticated.collectAsState()

    // When logging out from main app, automatically route back to Auth screen
    LaunchedEffect(isAuthenticated) {
        if (!isAuthenticated) {
            val currentRoute = navController.currentBackStackEntry?.destination?.route
            if (currentRoute == LoopRoute.MainApp.route) {
                navController.navigate(LoopRoute.Auth.route) {
                    popUpTo(LoopRoute.MainApp.route) { inclusive = true }
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { fadeIn() + slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
        exitTransition = { fadeOut() + slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
        popEnterTransition = { fadeIn() + slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End) },
        popExitTransition = { fadeOut() + slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End) }
    ) {
        // 1. Initial Splash Screen
        composable(LoopRoute.Splash.route) {
            SplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate(LoopRoute.Onboarding.route) {
                        popUpTo(LoopRoute.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // 2. Three-Step Value Proposition Onboarding UI
        composable(LoopRoute.Onboarding.route) {
            AppOnboardingScreen(
                onFinish = {
                    navController.navigate(LoopRoute.Auth.route) {
                        popUpTo(LoopRoute.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        // 3. Auth Page (Phone OTP + Demo Accounts)
        composable(LoopRoute.Auth.route) {
            AuthScreen(
                onOpenOnboarding = {
                    navController.navigate(LoopRoute.Onboarding.route)
                },
                onLoginSuccess = {
                    onLoginSuccess()
                    navController.navigate(LoopRoute.MainApp.route) {
                        popUpTo(LoopRoute.Auth.route) { inclusive = true }
                    }
                }
            )
        }

        // 4. Authenticated Main Application Flow
        composable(LoopRoute.MainApp.route) {
            mainAppContent()
        }
    }
}

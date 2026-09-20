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
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.data.UserRole

/**
 * Type-safe / sealed navigation routes for the Loop app navigation graph.
 * Manages the flow: Splash -> Onboarding -> Auth -> MainApp.
 */
sealed class LoopRoute(val route: String) {
    object Splash : LoopRoute("splash")
    object Onboarding : LoopRoute("onboarding")
    object Auth : LoopRoute("auth")
    object Registration : LoopRoute("registration")
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
                navController.navigate("${LoopRoute.Auth.route}/${UserRole.CUSTOMER.name}") {
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
        composable(LoopRoute.Splash.route) {
            SplashScreen(
                onNavigateToOnboarding = {
                    if (isAuthenticated) {
                        navController.navigate(LoopRoute.MainApp.route) {
                            popUpTo(LoopRoute.Splash.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(LoopRoute.Onboarding.route) {
                            popUpTo(LoopRoute.Splash.route) { inclusive = true }
                        }
                    }
                }
            )
        }

        // 2. Three-Step Value Proposition Onboarding UI
        composable(LoopRoute.Onboarding.route) {
            AppOnboardingScreen(
                onFinish = { selectedRole ->
                    navController.navigate("${LoopRoute.Auth.route}/${selectedRole.name}") {
                        popUpTo(LoopRoute.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        // 3. Auth Page (Phone OTP + Demo Accounts)
        composable(
            route = "${LoopRoute.Auth.route}/{role}",
            arguments = listOf(navArgument("role") { type = NavType.StringType })
        ) { backStackEntry ->
            val roleStr = backStackEntry.arguments?.getString("role") ?: UserRole.CUSTOMER.name
            val role = try { UserRole.valueOf(roleStr) } catch (e: Exception) { UserRole.CUSTOMER }

            AuthScreen(
                initialRole = role,
                onOpenOnboarding = {
                    navController.navigate(LoopRoute.Onboarding.route)
                },
                onLoginSuccess = {
                    onLoginSuccess()
                    navController.navigate(LoopRoute.MainApp.route) {
                        popUpTo("${LoopRoute.Auth.route}/{role}") { inclusive = true }
                    }
                },
                onRegistrationRequested = { reqRole, name, phone ->
                    navController.navigate("${LoopRoute.Registration.route}/${reqRole.name}?name=$name&phone=$phone") {
                        popUpTo("${LoopRoute.Auth.route}/{role}") { inclusive = true }
                    }
                }
            )
        }

        // Registration Screen
        composable(
            route = "${LoopRoute.Registration.route}/{role}?name={name}&phone={phone}",
            arguments = listOf(
                navArgument("role") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType; defaultValue = "" },
                navArgument("phone") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val roleStr = backStackEntry.arguments?.getString("role") ?: UserRole.CUSTOMER.name
            val regRole = try { UserRole.valueOf(roleStr) } catch (e: Exception) { UserRole.CUSTOMER }
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val phone = backStackEntry.arguments?.getString("phone") ?: ""

            com.example.ui.screens.RegistrationScreen(
                initialRole = regRole,
                initialName = name,
                initialPhone = phone,
                onRegistrationComplete = {
                    onLoginSuccess()
                    navController.navigate(LoopRoute.MainApp.route) {
                        popUpTo("${LoopRoute.Registration.route}/{role}?name={name}&phone={phone}") { inclusive = true }
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

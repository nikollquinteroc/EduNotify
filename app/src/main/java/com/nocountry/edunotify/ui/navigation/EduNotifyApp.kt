package com.nocountry.edunotify.ui.navigation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.nocountry.edunotify.domain.model.AuthDomain
import com.nocountry.edunotify.ui.components.TabRowComponent
import com.nocountry.edunotify.ui.navigation.Destinations.LOGIN_ROUTE
import com.nocountry.edunotify.ui.navigation.Destinations.NOTIFICATIONS_AUTH_DOMAIN
import com.nocountry.edunotify.ui.navigation.Destinations.NOTIFICATIONS_ROUTE
import com.nocountry.edunotify.ui.navigation.Destinations.NOTIFICATION_DETAIL_ROUTE
import com.nocountry.edunotify.ui.navigation.Destinations.NOTIFICATION_ID
import com.nocountry.edunotify.ui.navigation.Destinations.REGISTER_ROUTE
import com.nocountry.edunotify.ui.navigation.Destinations.SCHOOL_ID
import com.nocountry.edunotify.ui.navigation.Destinations.TAB_LOGIN_REGISTER
import com.nocountry.edunotify.ui.screens.courses.CoursesScreen
import com.nocountry.edunotify.ui.screens.detail.NotificationDetailScreen
import com.nocountry.edunotify.ui.screens.login.LoginScreen
import com.nocountry.edunotify.ui.screens.notifications.NotificationsScreen
import com.nocountry.edunotify.ui.screens.profile.ProfileScreen
import com.nocountry.edunotify.ui.screens.register.RegisterScreen

@Composable
fun EduNotifyApp(navController: NavHostController = rememberNavController()) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = TAB_LOGIN_REGISTER
        ) {
            composable(route = TAB_LOGIN_REGISTER) {
                TabRowComponent(navController = navController)
            }
            composable(route = LOGIN_ROUTE) {
                LoginScreen(
                    navigateToNotifications = { authDomain ->
                        val json = Uri.encode(Gson().toJson(authDomain))
                        navController.navigate("${NOTIFICATIONS_ROUTE}/$json") {
                            popUpTo(LOGIN_ROUTE) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onRegisterClicked = {
                        navController.navigate(REGISTER_ROUTE) {
                            launchSingleTop = true
                        }
                    })
            }
            composable(route = REGISTER_ROUTE) {
                RegisterScreen(
                    navigateToNotifications = { authDomain ->
                        val json = Uri.encode(Gson().toJson(authDomain))
                        navController.navigate("${NOTIFICATIONS_ROUTE}/$json") {
                            popUpTo(REGISTER_ROUTE) { inclusive = true }
                            launchSingleTop = true
                        }
                    })
            }
            composable(
                route = "$NOTIFICATIONS_ROUTE/{${NOTIFICATIONS_AUTH_DOMAIN}}",
                arguments = listOf(
                    navArgument(NOTIFICATIONS_AUTH_DOMAIN) {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val json =
                    requireNotNull(backStackEntry.arguments?.getString(NOTIFICATIONS_AUTH_DOMAIN))
                val authDomain = Gson().fromJson(json, AuthDomain::class.java)

                NotificationsScreen(
                    navController = navController,
                    onPlusClicked = { schooldId -> navController.navigate("${Destinations.COURSES_ROUTE}/$schooldId") },
                    onNotificationClicked = { notificationId ->
                        navController.navigate("$NOTIFICATION_DETAIL_ROUTE/$notificationId") {
                            popUpTo("$NOTIFICATIONS_ROUTE/{${NOTIFICATIONS_AUTH_DOMAIN}}")
                        }
                    },
                    authDomain = authDomain
                )
            }
            composable(
                route = "$NOTIFICATION_DETAIL_ROUTE/{$NOTIFICATION_ID}",
                arguments = listOf(
                    navArgument(NOTIFICATION_ID) { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val notificationId =
                    requireNotNull(backStackEntry.arguments?.getInt(NOTIFICATION_ID))

                val authDomainJson = navController.previousBackStackEntry?.arguments?.getString(
                    NOTIFICATIONS_AUTH_DOMAIN
                )

                val authDomain = if (authDomainJson != null) {
                    Gson().fromJson(authDomainJson, AuthDomain::class.java)
                } else {
                    null
                }

                if (authDomain == null) {
                    // Maneja el caso cuando authDomain es nulo. Podrías mostrar un mensaje de error o navegar hacia atrás.
                    navController.navigateUp()
                    return@composable
                }

                NotificationDetailScreen(
                    notificationId = notificationId,
                    authDomain = authDomain,
                    onNotificationSelected = { id -> navController.navigate("$NOTIFICATION_DETAIL_ROUTE/$id") },
                    onBackClicked = { navController.navigateUp() },
                )
            }
            composable(route = Destinations.PROFILE_ROUTE) {
                ProfileScreen(
                    navController = navController,
                    onBackClicked = { navController.navigateUp() })
            }
            composable(
                route = "${Destinations.COURSES_ROUTE}/{${SCHOOL_ID}}",
                arguments = listOf(navArgument(SCHOOL_ID) { type = NavType.IntType })
            ) { backStackEntry ->
                val schoolId = requireNotNull(backStackEntry.arguments?.getInt(SCHOOL_ID))

                val authDomainJson = navController.previousBackStackEntry?.arguments?.getString(
                    NOTIFICATIONS_AUTH_DOMAIN
                )
                val authDomain = try {
                    if (authDomainJson.isNullOrEmpty()) null else Gson().fromJson(
                        authDomainJson,
                        AuthDomain::class.java
                    )
                } catch (e: JsonSyntaxException) {
                    null
                }

                if (authDomain == null) {
                    // Maneja el caso cuando authDomain es nulo o no válido
                    navController.navigateUp()
                    return@composable
                }
                CoursesScreen(
                    onBackClicked = { navController.navigateUp() },
                    schoolId = schoolId,
                    userId = authDomain.user?.id ?: 0,
                    onAddCoursesClicked = { navController.navigate("$NOTIFICATIONS_ROUTE/{${NOTIFICATIONS_AUTH_DOMAIN}}") })
            }
        }
    }
}
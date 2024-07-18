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
import com.nocountry.edunotify.domain.model.AuthDomain
import com.nocountry.edunotify.ui.components.TabRowComponent
import com.nocountry.edunotify.ui.navigation.Destinations.LOGIN_ROUTE
import com.nocountry.edunotify.ui.navigation.Destinations.NOTIFICATIONS_AUTH_DOMAIN
import com.nocountry.edunotify.ui.navigation.Destinations.NOTIFICATIONS_ROUTE
import com.nocountry.edunotify.ui.navigation.Destinations.NOTIFICATION_DETAIL_ROUTE
import com.nocountry.edunotify.ui.navigation.Destinations.NOTIFICATION_ID
import com.nocountry.edunotify.ui.navigation.Destinations.REGISTER_ROUTE
import com.nocountry.edunotify.ui.navigation.Destinations.TAB_LOGIN_REGISTER
import com.nocountry.edunotify.ui.screens.courses.CoursesScreen
import com.nocountry.edunotify.ui.screens.courses.schoolTest1
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
                        navController.navigate("${NOTIFICATIONS_ROUTE}/$json")
                    },
                    onRegisterClicked = { navController.navigate(REGISTER_ROUTE) })
            }
            composable(route = REGISTER_ROUTE) {
                RegisterScreen(
                    navigateToNotifications = { authDomain ->
                        val json = Uri.encode(Gson().toJson(authDomain))
                        navController.navigate("${NOTIFICATIONS_ROUTE}/$json")
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
                val json = requireNotNull(backStackEntry.arguments?.getString(NOTIFICATIONS_AUTH_DOMAIN))
                val authDomain = Gson().fromJson(json, AuthDomain::class.java)

                NotificationsScreen(
                    navController = navController,
                    onPlusClicked = { navController.navigate(Destinations.COURSES_ROUTE) },
                    onNotificationClicked = { notificationId -> navController.navigate("$NOTIFICATION_DETAIL_ROUTE/$notificationId") },
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

                NotificationDetailScreen(
                    onBackClicked = { navController.popBackStack() },
                    notificationMock = null
                )
            }
            composable(route = Destinations.PROFILE_ROUTE) {
                ProfileScreen(
                    navController = navController,
                    onBackClicked = { navController.popBackStack() })
            }
            composable(route = Destinations.COURSES_ROUTE) {
                CoursesScreen(
                    schoolTest = schoolTest1,
                    onAddCoursesClicked = { navController.navigate(NOTIFICATIONS_ROUTE) })
            }
        }
    }
}
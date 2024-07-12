package com.nocountry.edunotify.ui.navigation

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
import com.nocountry.edunotify.ui.components.TabRowComponent
import com.nocountry.edunotify.ui.screens.courses.CoursesScreen
import com.nocountry.edunotify.ui.screens.courses.school1
import com.nocountry.edunotify.ui.screens.detail.NotificationDetailScreen
import com.nocountry.edunotify.ui.screens.login.LoginScreen
import com.nocountry.edunotify.ui.screens.notifications.NotificationsScreen
import com.nocountry.edunotify.ui.screens.notifications.notifications
import com.nocountry.edunotify.ui.screens.profile.ProfileScreen
import com.nocountry.edunotify.ui.screens.register.RegisterScreen
import com.nocountry.edunotify.ui.screens.register.schools

@Composable
fun EduNotifyApp(navController: NavHostController = rememberNavController()) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = Destinations.TAB_LOGIN_REGISTER
        ) {
            composable(route = Destinations.TAB_LOGIN_REGISTER) {
                TabRowComponent(navController = navController)
            }
            composable(route = Destinations.LOGIN_ROUTE) {
                LoginScreen(
                    onLoginClicked = { navController.navigate(Destinations.NOTIFICATIONS_ROUTE) },
                    onRegisterClicked = { navController.navigate(Destinations.REGISTER_ROUTE) })
            }
            composable(route = Destinations.REGISTER_ROUTE) {
                RegisterScreen(
                    schools = schools,
                    onRegisterClicked = { navController.navigate(Destinations.NOTIFICATIONS_ROUTE) })
            }
            composable(route = Destinations.NOTIFICATIONS_ROUTE) {
                NotificationsScreen(
                    navController = navController,
                    onPlusClicked = { navController.navigate(Destinations.COURSES_ROUTE) },
                    onNotificationClicked = { notificationId -> navController.navigate("${Destinations.NOTIFICATION_DETAIL_ROUTE}/$notificationId") },
                    notifications = notifications
                )
            }
            composable(
                route = "${Destinations.NOTIFICATION_DETAIL_ROUTE}/{${Destinations.NOTIFICATION_ID}}",
                arguments = listOf(
                    navArgument(Destinations.NOTIFICATION_ID) { type = NavType.IntType })
            ) { backStackEntry ->
                val notificationId =
                    requireNotNull(backStackEntry.arguments?.getInt(Destinations.NOTIFICATION_ID))
                val notification = notifications.firstOrNull { it.id == notificationId }
                    ?: error("Notification not found")
                NotificationDetailScreen(
                    onBackClicked = { navController.popBackStack() },
                    notification = notification
                )
            }
            composable(route = Destinations.PROFILE_ROUTE) {
                ProfileScreen(
                    navController = navController,
                    onBackClicked = { navController.popBackStack() })
            }
            composable(route = Destinations.COURSES_ROUTE) {
                CoursesScreen(
                    school = school1,
                    onAddCoursesClicked = { navController.navigate(Destinations.NOTIFICATIONS_ROUTE) })
            }
        }
    }
}
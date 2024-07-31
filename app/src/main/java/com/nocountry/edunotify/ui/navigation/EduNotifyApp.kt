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
import com.google.gson.Gson
import com.nocountry.edunotify.domain.model.NotificationDomain
import com.nocountry.edunotify.domain.model.UserDomain
import com.nocountry.edunotify.ui.components.TabRowComponent
import com.nocountry.edunotify.ui.navigation.Destinations.LOGIN_ROUTE
import com.nocountry.edunotify.ui.navigation.Destinations.NEW_NOTIFICATIONS_ROUTE
import com.nocountry.edunotify.ui.navigation.Destinations.NOTIFICATIONS_ROUTE
import com.nocountry.edunotify.ui.navigation.Destinations.NOTIFICATION_DETAIL_ROUTE
import com.nocountry.edunotify.ui.navigation.Destinations.NOTIFICATION_DOMAIN_DETAIL
import com.nocountry.edunotify.ui.navigation.Destinations.PROFILE_ROUTE
import com.nocountry.edunotify.ui.navigation.Destinations.REGISTER_ROUTE
import com.nocountry.edunotify.ui.navigation.Destinations.SCHOOL_ID
import com.nocountry.edunotify.ui.navigation.Destinations.TAB_LOGIN_REGISTER
import com.nocountry.edunotify.ui.navigation.Destinations.USER_DOMAIN
import com.nocountry.edunotify.ui.navigation.Destinations.USER_ID
import com.nocountry.edunotify.ui.screens.courses.CoursesScreen
import com.nocountry.edunotify.ui.screens.detail.NotificationDetailScreen
import com.nocountry.edunotify.ui.screens.login.LoginScreen
import com.nocountry.edunotify.ui.screens.new_notification.CreateNotificationScreen
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
                        navController.navigate("${NOTIFICATIONS_ROUTE}/${authDomain.user?.id}") {
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
                        navController.navigate("${NOTIFICATIONS_ROUTE}/${authDomain.user?.id}") {
                            popUpTo(REGISTER_ROUTE) { inclusive = true }
                            launchSingleTop = true
                        }
                    })
            }
            composable(
                route = "$NOTIFICATIONS_ROUTE/{${USER_ID}}",
                arguments = listOf(
                    navArgument(USER_ID) {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val userId = requireNotNull(backStackEntry.arguments?.getInt(USER_ID))

                NotificationsScreen(
                    navController = navController,
                    onSubscribeToANewCourse = { schoolId -> navController.navigate("${Destinations.COURSES_ROUTE}/$schoolId/$userId") },
                    onGoToNotificationDetail = { notificationDomain ->
                        val notificationJson = Gson().toJson(notificationDomain)
                        navController.navigate("$NOTIFICATION_DETAIL_ROUTE/$notificationJson") {
                            popUpTo("$NOTIFICATIONS_ROUTE/$userId")
                        }
                    },
                    userId = userId
                )
            }
            composable(
                route = "$NOTIFICATION_DETAIL_ROUTE/{$NOTIFICATION_DOMAIN_DETAIL}",
                arguments = listOf(
                    navArgument(NOTIFICATION_DOMAIN_DETAIL) { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val notificationDomainJson =
                    requireNotNull(backStackEntry.arguments?.getString(NOTIFICATION_DOMAIN_DETAIL))

                val notificationDomain =
                    Gson().fromJson(notificationDomainJson, NotificationDomain::class.java)

                NotificationDetailScreen(
                    notificationDomain = notificationDomain,
                    onBackClicked = { navController.navigateUp() },
                )
            }
            composable(
                route = "${PROFILE_ROUTE}/{${USER_DOMAIN}}",
                arguments = listOf(navArgument(USER_DOMAIN) {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val userDomainJson =
                    requireNotNull(backStackEntry.arguments?.getString(USER_DOMAIN))
                val userDomain = Gson().fromJson(userDomainJson, UserDomain::class.java)

                ProfileScreen(
                    navController = navController,
                    onBackClicked = { navController.navigateUp() },
                    userDomain = userDomain
                )
            }
            composable(
                route = "${Destinations.COURSES_ROUTE}/{${SCHOOL_ID}}/{${USER_ID}}",
                arguments = listOf(
                    navArgument(SCHOOL_ID) { type = NavType.IntType },
                    navArgument(USER_ID) { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val schoolId = requireNotNull(backStackEntry.arguments?.getInt(SCHOOL_ID))
                val userId = requireNotNull(backStackEntry.arguments?.getInt(USER_ID))

                CoursesScreen(
                    onBackClicked = { navController.navigateUp() },
                    schoolId = schoolId,
                    userId = userId,
                    onAddCoursesClicked = { navController.navigate("$NOTIFICATIONS_ROUTE/$userId") })
            }
            composable(
                "${NEW_NOTIFICATIONS_ROUTE}/{${USER_DOMAIN}}",
                arguments = listOf(navArgument(USER_DOMAIN) {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val userDomainJson =
                    requireNotNull(backStackEntry.arguments?.getString(USER_DOMAIN))
                val userDomain = Gson().fromJson(userDomainJson, UserDomain::class.java)
                CreateNotificationScreen(
                    navController = navController,
                    onBackClicked = { navController.navigateUp() },
                    userDomain = userDomain
                )
            }
        }
    }
}
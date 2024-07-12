package com.nocountry.edunotify.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.nocountry.edunotify.R
import com.nocountry.edunotify.data.utils.UserRole
import com.nocountry.edunotify.domain.model.UserDomain
import com.nocountry.edunotify.ui.navigation.Destinations
import com.nocountry.edunotify.ui.screens.notifications.fakeUserDomain
import com.nocountry.edunotify.ui.theme.EduNotifyTheme

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    userDomain: UserDomain,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavigationBarItem(
                icon = R.drawable.notifications_icon,
                name = R.string.bottom_notification_bar,
                onClick = {
                    navController.navigate("${Destinations.NOTIFICATIONS_ROUTE}/${userDomain.id}")
                }
            )
            VerticalDivider(
                color = MaterialTheme.colorScheme.primary,
                thickness = 2.dp,
                modifier = Modifier.fillMaxHeight()
            )
            if (userDomain.role == UserRole.COLABORADOR) {
                BottomNavigationBarItem(
                    icon = R.drawable.document_icon,
                    name = R.string.bottom_new_notification,
                    onClick = {
                        val userDomainJson = Gson().toJson(userDomain)
                        navController.navigate("${Destinations.NEW_NOTIFICATIONS_ROUTE}/$userDomainJson/${userDomain.id}") {
                            popUpTo(Destinations.PROFILE_ROUTE) { inclusive = true }
                        }
                    }
                )
                VerticalDivider(
                    color = MaterialTheme.colorScheme.primary,
                    thickness = 2.dp,
                    modifier = Modifier.fillMaxHeight()
                )
            }
            BottomNavigationBarItem(
                icon = R.drawable.profile_icon,
                name = R.string.bottom_profile_bar,
                onClick = {
                    val userDomainJson = Gson().toJson(userDomain)
                    navController.navigate("${Destinations.PROFILE_ROUTE}/$userDomainJson") {
                        popUpTo(Destinations.PROFILE_ROUTE) { inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
fun BottomNavigationBarItem(
    icon: Int,
    name: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .width(100.dp)
            .height(70.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp),
                contentScale = ContentScale.Fit
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(id = name),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    maxLines = 1
                )
            }
        }
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    EduNotifyTheme {
        BottomNavigationBar(
            navController = rememberNavController(),
            userDomain = fakeUserDomain
        )
    }
}
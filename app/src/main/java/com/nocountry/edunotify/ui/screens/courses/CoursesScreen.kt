package com.nocountry.edunotify.ui.screens.courses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nocountry.edunotify.ui.theme.EduNotifyTheme

@Composable
fun CoursesScreen(onAddCoursesClicked: () -> Unit) {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Courses Screen")
            Button(onClick = onAddCoursesClicked) {
                Text(text = "add courses")
            }
        }
    }
}


@Preview
@Composable
fun CoursesScreenPreview() {
    EduNotifyTheme {
        CoursesScreen(onAddCoursesClicked = {})
    }
}